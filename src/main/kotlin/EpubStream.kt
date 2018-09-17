import io.reactivex.Completable
import io.reactivex.Single
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class EpubStream(val file : File) {

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

    fun getMimeType() : Single<Any> {
        return Single.just(1)
    }

    fun getContainer() : Single<Any> {
        return Single.just(1)
    }

    fun getOpf() : Single<Any> {
        return Single.just(1)
    }

    fun getToc() : Single<Any> {
        return Single.just(1)
    }

    fun getFile() : Single<Any> {
        return Single.just(1)
    }

}