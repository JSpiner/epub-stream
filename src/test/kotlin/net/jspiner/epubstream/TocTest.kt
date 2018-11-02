package net.jspiner.epubstream

import net.jspiner.epubstream.dto.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File

class TocTest {

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
    fun tocGetTest() {
        epubStream.getToc()
            .test()
            .assertComplete()
    }

    @Test
    fun tocGetHeadTest() {
        val expectedHead = Head(
            arrayOf(
                Meta("dtb:uid", "http://beyondgoodandevil.pressbooks.com", null),
                Meta("dtb:depth", "2", null),
                Meta("dtb:totalPageCount", "0", null),
                Meta("dtb:maxPageNumber", "0", null)
            )
        )
        epubStream.getToc()
            .map { it.head }
            .test()
            .assertValue {
                return@assertValue it.metas contentEquals expectedHead.metas
            }
    }

    @Test
    fun tocGetDocTitleTest() {
        val expectedDocTitle = DocTitle(
            null,
            "Beyond Good and Evil",
            null
        )
        epubStream.getToc()
            .map { it.docTitle }
            .test()
            .assertValue(expectedDocTitle)
    }

    @Test
    fun tocGetDocAuthorTest() {
        val expectedDocAuthor = DocAuthor(
            null,
            "Friedrich Nietzsche",
            null
        )
        epubStream.getToc()
            .map { it.docAuthor }
            .test()
            .assertValue(expectedDocAuthor)
    }

    @Test
    fun tocGetNavMapTest() {
        val expectedNavMap = NavMap(
            null,
            arrayOf(
                NavPoint("front-cover", "1", NavLabel("Cover", null), Content(null, "./test/OEBPS/front-cover.html")),
                NavPoint(
                    "title-page",
                    "2",
                    NavLabel("Title Page", null),
                    Content(null, "./test/OEBPS/title-page.html")
                ),
                NavPoint("copyright", "3", NavLabel("Copyright", null), Content(null, "./test/OEBPS/copyright.html")),
                NavPoint(
                    "table-of-contents",
                    "4",
                    NavLabel("Table Of Contents", null),
                    Content(null, "./test/OEBPS/table-of-contents.html")
                ),
                NavPoint(
                    "front-matter-001",
                    "5",
                    NavLabel("Preface", null),
                    Content(null, "./test/OEBPS/front-matter-001-preface.html")
                ),
                NavPoint(
                    "pressbooks-promo",
                    "6",
                    NavLabel("Make your own books using PressBooks.com", null),
                    Content(null, "./test/OEBPS/pressbooks-promo.html")
                ),
                NavPoint(
                    "chapter-001",
                    "7",
                    NavLabel("Chapter I", null),
                    Content(null, "./test/OEBPS/chapter-001-chapter-i.html")
                ),
                NavPoint(
                    "chapter-002",
                    "8",
                    NavLabel("Chapter II", null),
                    Content(null, "./test/OEBPS/chapter-002-chapter-ii.html")
                ),
                NavPoint(
                    "chapter-003",
                    "9",
                    NavLabel("Chapter III", null),
                    Content(null, "./test/OEBPS/chapter-003-chapter-iii.html")
                ),
                NavPoint(
                    "chapter-004",
                    "10",
                    NavLabel("Chapter IV", null),
                    Content(null, "./test/OEBPS/chapter-004-chapter-iv.html")
                ),
                NavPoint(
                    "chapter-005",
                    "11",
                    NavLabel("Chapter V", null),
                    Content(null, "./test/OEBPS/chapter-005-chapter-v.html")
                ),
                NavPoint(
                    "chapter-006",
                    "12",
                    NavLabel("Chapter VI", null),
                    Content(null, "./test/OEBPS/chapter-006-chapter-vi.html")
                ),
                NavPoint(
                    "chapter-007",
                    "13",
                    NavLabel("Chapter VII", null),
                    Content(null, "./test/OEBPS/chapter-007-chapter-vii.html")
                ),
                NavPoint(
                    "chapter-008",
                    "14",
                    NavLabel("Chapter VIII", null),
                    Content(null, "./test/OEBPS/chapter-008-chapter-viii.html")
                ),
                NavPoint(
                    "chapter-009",
                    "15",
                    NavLabel("Chapter IX", null),
                    Content(null, "./test/OEBPS/chapter-009-chapter-ix.html")
                ),
                NavPoint(
                    "back-matter-001",
                    "16",
                    NavLabel("From the Heights", null),
                    Content(null, "./test/OEBPS/back-matter-001-from-the-heights.html")
                )
            )
        )

        epubStream.getToc()
            .map { it.navMap }
            .test()
            .assertValue {
                it.id == expectedNavMap.id && it.navPoints contentEquals expectedNavMap.navPoints
            }
    }
}