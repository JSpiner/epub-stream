package net.jspiner.epubstream

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import net.jspiner.epubstream.dto.*
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import kotlin.collections.HashMap


class EpubStream(val file: File) {

    private val MIMETYPE_FILE_NAME = "mimetype"
    private val CONTAINER_FILE_NAME = "META-INF/container.xml"

    private var extractedDirectory: File? = null

    private var mimeType: MimeType? = null
    private var container: Container? = null
    private var opf: Package? = null

    fun unzip(outputPath: String = "./" + file.nameWithoutExtension): Completable {
        if (!file.exists()) return Completable.error(NoSuchFileException(file))

        return Completable.create { emitter ->
            val inputStream = ZipInputStream(FileInputStream(file))
            while (true) {
                val entry: ZipEntry = inputStream.nextEntry ?: break
                val newFile = File(outputPath + File.separator + entry.name)

                File(newFile.parent).mkdirs()
                extractFile(inputStream, newFile)
                inputStream.closeEntry()
            }
            inputStream.close()

            extractedDirectory = File(outputPath)
            emitter.onComplete()
        }
    }

    private fun extractFile(zipIn: ZipInputStream, newFile: File) {
        val outputStream = BufferedOutputStream(FileOutputStream(newFile))
        val bytesIn = ByteArray(2048)
        var read = 0
        while ((zipIn.read(bytesIn).also { read = it }) != -1) {
            outputStream.write(bytesIn, 0, read)
        }
        outputStream.close()
    }

    fun getExtractedDirectory(): Single<File> {
        return if (extractedDirectory != null) {
            Single.just(extractedDirectory)
        } else {
            unzip().toSingle { extractedDirectory }
        }
    }

    fun getMimeType(): Single<MimeType> {
        return if (mimeType != null) {
            Single.just(mimeType)
        } else {
            getExtractedDirectory()
                    .map { it.toPath().resolve(MIMETYPE_FILE_NAME) }
                    .map { Files.readAllLines(it).joinToString() }
                    .map { MimeType(it) }
                    .doOnSuccess { this@EpubStream.mimeType = it }
        }
    }

    fun getContainer(): Single<Container> {
        return if (container != null) {
            Single.just(container)
        } else {
            getMimeType()
                    .flatMap { getExtractedDirectory() }
                    .map { it.resolve(CONTAINER_FILE_NAME) }
                    .map { parseToDocument(it) }
                    .map { evaluateNodeList(it, "/container/rootfiles/rootfile") }
                    .map { nodeList ->
                        List(nodeList.length) { it -> nodeList.item(it) }
                                .map { node ->
                                    RootFile(
                                            node.getProperty("full-path")!!,
                                            node.getProperty("media-type")!!
                                    )
                                }
                    }
                    .map { Container(it.toTypedArray()) }
                    .doOnSuccess { this@EpubStream.container = it }
        }
    }

    fun getOpf(): Single<Package> {
        return if (opf != null) {
            Single.just(opf)
        } else {
            getContainer()
                    .zipWith(getExtractedDirectory(), BiFunction { container: Container, file: File -> container to file })
                    .map { pair -> pair.second.resolve(pair.first.rootFiles[0].fullPath) }
                    .map { parseToDocument(it) }
                    .map { document ->
                        val packageNode = evaluateNode(document, "/package")!!
                        return@map Package(
                                packageNode.getProperty("version")!!,
                                packageNode.getProperty("unique-identifier")!!,
                                parseMetadata(document),
                                parseManifest(document),
                                parseSpine(document),
                                parseGuide(document)
                        )
                    }
        }
    }

    fun parseMetadata(document: Document): MetaData {
        val META_PATH = "/package/metadata/"
        fun getChildNode(nodeName: String): Node? = evaluateNode(document, "$META_PATH$nodeName")
        fun getChildNodeList(nodeName: String): NodeList? = evaluateNodeList(document, "$META_PATH$nodeName")

        return MetaData(
                getChildNode("title")!!.textContent,
                parseCreator(getChildNode("creator")),
                parseSubject(getChildNodeList("subject")),
                getChildNode("description")?.textContent,
                getChildNode("publisher")?.textContent,
                parseCreator(getChildNode("contributor")),
                parseDate(getChildNode("date")?.textContent),
                getChildNode("type")?.textContent,
                getChildNode("fotmat")?.textContent,
                parseIdentifier(getChildNode("identifier")!!),
                getChildNode("source")?.textContent,
                getChildNode("language")!!.textContent,
                getChildNode("releation")?.textContent,
                getChildNode("coverage")?.textContent,
                getChildNode("rights")?.textContent,
                parseMeta(document)
        )
    }

    fun parseCreator(node: Node?): Creator? {
        if (node == null) return null

        val role = node.getProperty("opf:role")
        return Creator(
                node.getProperty("opf:file-as"),
                if (role != null) RoleType.valueOf(role) else null,
                node.textContent
        )
    }

    fun parseSubject(nodeList: NodeList?): Array<String>? {
        if (nodeList == null) return null

        return Array(nodeList.length) {
            nodeList.item(it).textContent
        }
    }

    fun parseDate(date: String?): Date? {
        if (date == null) return null

        val ableFormats = arrayListOf(
                "yyyy",
                "yyyy-MM",
                "yyyy-MM-dd"
        )
        for (format in ableFormats) {
            try {
                return SimpleDateFormat(format).parse(date)
            } catch (_: ParseException) {
                //no-op
            }
        }
        throw ParseException(date, 0)
    }

    fun parseIdentifier(node: Node): Identifier {
        return Identifier(
                node.getProperty("id")!!,
                node.getProperty("opf:scheme"),
                node.textContent
        )
    }

    fun parseMeta(document: Document): HashMap<String, String> {
        val nodeList = evaluateNodeList(document, "/package/metadata/meta")
        val hashMap = HashMap<String, String>()
        for (i in 0 until nodeList.length) {
            hashMap.put(
                    nodeList.item(i).getProperty("name")!!,
                    nodeList.item(i).getProperty("content")!!
            )
        }
        return hashMap
    }

    private fun parseManifest(document: Document): Manifest {
        val nodeList = evaluateNodeList(document, "/package/manifest/item")
        val items = Array(nodeList.length) {
            val node = nodeList.item(it)
            return@Array Item(
                    node.getProperty("id")!!,
                    node.getProperty("href")!!,
                    node.getProperty("media-type")!!
            )
        }

        return Manifest(items)
    }

    private fun parseSpine(document: Document): Spine {
        val node = evaluateNode(document, "/package/spine")!!
        val nodeList = evaluateNodeList(document, "/package/spine/itemref")
        val items = Array(nodeList.length) {
            val node = nodeList.item(it)
            return@Array ItemRef(
                    node.getProperty("idref")!!,
                    node.getProperty("linear") ?: "yes"
            )
        }

        return Spine(
                node.getProperty("toc")!!,
                items
        )
    }

    private fun parseGuide(document: Document): Guide? {
        val nodeList = evaluateNodeList(document, "/package/guide/reference")
        val items = Array(nodeList.length) {
            val node = nodeList.item(it)
            return@Array Reference(
                    node.getProperty("type"),
                    node.getProperty("title"),
                    node.getProperty("href")!!
            )
        }

        return Guide(items)
    }

    fun getToc(): Single<Any> {
        return Single.just(1)
    }

    fun getFile(): Single<Any> {
        return Single.just(1)
    }

}