package net.jspiner.epubstream

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File

class MimeTypeTest {

    private val EPUB_TEST_FILE = "src/test/resources/test.epub"
    private var epubStream: EpubStream = EpubStream(File(EPUB_TEST_FILE))

    @Before
    fun setUp() {
        epubStream = EpubStream(File(EPUB_TEST_FILE))
        epubStream.unzip().subscribe()
    }

    @After
    fun tearDown() {
        val testFile = File(EPUB_TEST_FILE)
        val outputDirectory = File("./" + testFile.nameWithoutExtension)
        outputDirectory.deleteRecursively()
    }

    @Test
    fun mimeTypeGetTest() {
        epubStream.getMimeType()
            .map { it.type }
            .test()
            .assertValue("application/epub+zip")
    }

    @Test
    fun mimeTypeGetFromCacheTest() {
        epubStream.getMimeType().subscribe()
        tearDown()

        epubStream.getMimeType()
            .map { it.type }
            .test()
            .assertValue("application/epub+zip")
    }
}