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
    }

    @Test
    fun unzipFileNotFoundTest() {
        var epubStream = EpubStream(File(NOT_EXIST_FILE))
        epubStream.unzip()
                .test()
                .assertError(NoSuchFileException::class.java)
    }

    @Test
    fun unzipNoErrorTest() {
        var epubStream = EpubStream(File(EPUB_TEST_FILE))
        epubStream.unzip()
                .test()
                .assertComplete()
    }

    @Test
    fun unzipOutputMatchTest() {
        var epubStream = EpubStream(File(EPUB_TEST_FILE))
        epubStream.unzip()
                .test()
                .assertOf {
                    val expect = FileUtils.listFiles(
                            File(EPUB_TEST_FILE_EXPECTED_DIRECTORY),
                            TrueFileFilter.INSTANCE,
                            TrueFileFilter.INSTANCE
                    ).map { it -> it.name }
                    val actual = FileUtils.listFiles(
                            File("./" + File(EPUB_TEST_FILE).nameWithoutExtension),
                            TrueFileFilter.INSTANCE,
                            TrueFileFilter.INSTANCE
                    ).map { it -> it.name }

                    assert(expect.containsAll(actual))
                }
    }

    @Test
    fun unzipDefinedOutputMatchTest() {
        var outputPath = "./" + File(EPUB_TEST_FILE).nameWithoutExtension
        var epubStream = EpubStream(File(EPUB_TEST_FILE))
        epubStream.unzip(outputPath)
                .test()
                .assertOf {
                    val expect = FileUtils.listFiles(
                            File(EPUB_TEST_FILE_EXPECTED_DIRECTORY),
                            TrueFileFilter.INSTANCE,
                            TrueFileFilter.INSTANCE
                    ).map { it -> it.name }
                    val actual = FileUtils.listFiles(
                            File(outputPath),
                            TrueFileFilter.INSTANCE,
                            TrueFileFilter.INSTANCE
                    ).map { it -> it.name }

                    assert(expect.containsAll(actual))
                }
    }

}