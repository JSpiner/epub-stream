package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.Item
import net.jspiner.epubstream.dto.Manifest
import net.jspiner.epubstream.evaluateNodeList
import net.jspiner.epubstream.getProperty
import org.w3c.dom.Document
import java.io.File

fun parseManifest(document: Document, manifestPath: String): Manifest {
    val nodeList = evaluateNodeList(document, "/package/manifest/item")
    val items = Array(nodeList.length) {
        val node = nodeList.item(it)
        var href = node.getProperty("href")!!
        if (!href.startsWith("/")) {
            href = manifestPath + File.separator + href
        }

        println(href)
        return@Array Item(
                node.getProperty("id")!!,
                href,
                node.getProperty("media-type")!!
        )
    }

    return Manifest(items)
}