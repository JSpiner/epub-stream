package net.jspiner.epubstream

import net.jspiner.epubstream.dto.Container
import net.jspiner.epubstream.dto.RootFile
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File

class ContainerTest {

    private val EPUB_TEST_FILE = "src/test/resources/test.epub"
    private val EXPECTED_CONTAINER = Container(arrayOf(RootFile("book.opf", "application/oebps-package+xml")))
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
    fun containerGetTest() {
        epubStream.getContainer()
            .test()
            .assertValue {
                it.rootFiles contentEquals EXPECTED_CONTAINER.rootFiles
            }
    }

    @Test
    fun containerGetFromCacheTest() {
        containerGetTest()

        tearDown()

        epubStream.getContainer()
            .test()
            .assertValue {
                it.rootFiles contentEquals EXPECTED_CONTAINER.rootFiles
            }
    }
}