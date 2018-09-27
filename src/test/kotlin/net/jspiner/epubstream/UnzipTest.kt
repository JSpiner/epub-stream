package net.jspiner.epubstream

import org.apache.commons.io.FileUtils
import org.apache.commons.io.filefilter.TrueFileFilter
import org.junit.After
import org.junit.Test
import java.io.File

class UnzipTest {

    private val EPUB_TEST_FILE = "src/test/resources/test.epub"
    private val NOT_EXIST_FILE = "src/test/resources/not_exist_file.epub"
    private val EPUB_TEST_FILE_EXPECTED_DIRECTORY = "src/test/resources/test_expected"

    @After
    fun tearDown() {
        val testFile = File(EPUB_TEST_FILE)
        val outputDirectory = File("./" + testFile.nameWithoutExtension)
        outputDirectory.deleteRecursively()

        val outputDirectory2 = File("./hello")
        outputDirectory2.deleteRecursively()
    }

    @Test
    fun unzipFileNotFoundTest() {
        val epubStream = EpubStream(File(NOT_EXIST_FILE))
        epubStream.unzip()
                .test()
                .assertError(NoSuchFileException::class.java)
    }

    @Test
    fun unzipNoErrorTest() {
        val epubStream = EpubStream(File(EPUB_TEST_FILE))
        epubStream.unzip()
                .test()
                .assertComplete()
    }

    @Test
    fun unzipOutputMatchTest() {
        val epubStream = EpubStream(File(EPUB_TEST_FILE))
        epubStream.unzip()
                .test()
                .assertOf {
                    val expect = FileUtils.listFiles(
                            File(EPUB_TEST_FILE_EXPECTED_DIRECTORY),
                            TrueFileFilter.INSTANCE,
                            TrueFileFilter.INSTANCE
                    ).map { file -> file.name }
                    val actual = FileUtils.listFiles(
                            File("./" + File(EPUB_TEST_FILE).nameWithoutExtension),
                            TrueFileFilter.INSTANCE,
                            TrueFileFilter.INSTANCE
                    ).map { file -> file.name }

                    assert(expect.containsAll(actual))
                }
    }

    @Test
    fun unzipDefinedOutputMatchTest() {
        val outputPath = "./" + File(EPUB_TEST_FILE).nameWithoutExtension
        val epubStream = EpubStream(File(EPUB_TEST_FILE))
        epubStream.unzip(outputPath)
                .test()
                .assertOf {
                    val expect = FileUtils.listFiles(
                            File(EPUB_TEST_FILE_EXPECTED_DIRECTORY),
                            TrueFileFilter.INSTANCE,
                            TrueFileFilter.INSTANCE
                    ).map { file -> file.name }
                    val actual = FileUtils.listFiles(
                            File(outputPath),
                            TrueFileFilter.INSTANCE,
                            TrueFileFilter.INSTANCE
                    ).map { file -> file.name }

                    assert(expect.containsAll(actual))
                }
    }

    @Test
    fun unzipDefinedDifferentOutputPathMatchTest() {
        val outputPath = "hello"
        val epubStream = EpubStream(File(EPUB_TEST_FILE))
        epubStream.unzip(outputPath)
                .toSingle { epubStream.getExtractedDirectory() }
                .flatMap { it -> it }
                .map { it -> it.path }
                .test()
                .assertValue(outputPath)
    }

    @Test
    fun unzipPathTest() {
        val epubStream = EpubStream(File(EPUB_TEST_FILE))
        epubStream.unzip()
                .toSingle { epubStream.getExtractedDirectory() }
                .flatMap { it -> it }
                .map { it -> it.path }
                .test()
                .assertValue("./" + File(EPUB_TEST_FILE).nameWithoutExtension)
    }

    @Test
    fun withoutUnzipPathTest() {
        val epubStream = EpubStream(File(EPUB_TEST_FILE))
        epubStream.getExtractedDirectory()
                .map { it -> it.path }
                .test()
                .assertValue("./" + File(EPUB_TEST_FILE).nameWithoutExtension)
    }

    @Test
    fun withoutUnzipPathAndOutputTest() {
        val epubStream = EpubStream(File(EPUB_TEST_FILE))
        epubStream.getExtractedDirectory()
                .map { it -> it.path }
                .test()
                .assertValue("./" + File(EPUB_TEST_FILE).nameWithoutExtension)

        unzipOutputMatchTest()
    }

    @Test
    fun afterUnzipDefinedDifferentOutputPathMatchTest() {
        val outputPath = "hello"
        val epubStream = EpubStream(File(EPUB_TEST_FILE))
        epubStream.unzip(outputPath).subscribe()

        epubStream.unzip()
                .toSingle { epubStream.getExtractedDirectory() }
                .flatMap { it -> it }
                .map { it -> it.path }
                .test()
                .assertValue(outputPath)
    }


}