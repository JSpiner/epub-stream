package net.jspiner.epubstream

import org.apache.commons.io.FileUtils
import org.apache.commons.io.filefilter.TrueFileFilter
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File

class ParseAllTest {

    private val EPUB_TEST_FILE = "src/test/resources/test.epub"
    private val EPUB_TEST_FILE_EXPECTED_DIRECTORY = "src/test/resources/test_expected"
    private var epubStream: EpubStream = EpubStream(File(EPUB_TEST_FILE))

    @Before
    fun setUp() {
        epubStream = EpubStream(File(EPUB_TEST_FILE))
    }

    @After
    fun tearDown() {
        val outputDirectory = File("./hello")
        outputDirectory.deleteRecursively()
    }

    @Test
    fun parseAllTest() {
        epubStream.unzip("hello")
                .andThen(epubStream.getMimeType())
                .flatMap { epubStream.getContainer() }
                .flatMap { epubStream.getOpf() }
                .flatMap { epubStream.getToc() }
                .test()
                .assertOf {
                    val expect = FileUtils.listFiles(
                            File(EPUB_TEST_FILE_EXPECTED_DIRECTORY),
                            TrueFileFilter.INSTANCE,
                            TrueFileFilter.INSTANCE
                    ).map { file -> file.name }
                    val actual = FileUtils.listFiles(
                            File("./hello/"),
                            TrueFileFilter.INSTANCE,
                            TrueFileFilter.INSTANCE
                    ).map { file -> file.name }

                    assert(expect.containsAll(actual))
                }
    }


}