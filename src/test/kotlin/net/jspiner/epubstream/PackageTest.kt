package net.jspiner.epubstream

import net.jspiner.epubstream.dto.Creator
import net.jspiner.epubstream.dto.Identifier
import net.jspiner.epubstream.dto.Item
import net.jspiner.epubstream.dto.ItemRef
import net.jspiner.epubstream.dto.Manifest
import net.jspiner.epubstream.dto.MetaData
import net.jspiner.epubstream.dto.RoleType
import net.jspiner.epubstream.dto.Spine
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File

class PackageTest {

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
    fun getPackageTest() {
        epubStream.getOpf()
            .test()
            .assertComplete()
    }

    @Test
    fun getPackageMetadataTest() {
        val expectedExtraMeta = HashMap<String, String>()
        expectedExtraMeta.put("cover", "cover-image")
        val expectedMetadata = MetaData(
            "Beyond Good and Evil",
            Creator(
                "Nietzsche, Friedrich",
                RoleType.aut,
                "Friedrich Nietzsche"
            ),
            arrayOf(),
            "<p>In <em>Beyond Good and Evil,</em> Nietzsche accuses past philosophers of lacking critical sense and blindly accepting dogmatic premises in their consideration of morality. Specifically, he accuses them of founding grand metaphysical systems upon the faith that the good man is the opposite of the evil man, rather than just a different expression of the same basic impulses that find more direct expression in the evil man. The work moves into the realm \"beyond good and evil\" in the sense of leaving behind the traditional morality which Nietzsche subjects to a destructive critique in favour of what he regards as an affirmative approach that fearlessly confronts the perspectival nature of knowledge and the perilous condition of the modern individual.(from Wikipedia)</p><div style=\"text-align: center;\"><p>Download: <br /><strong><a href=\"\">PDF</a> | <a href=\"\">EPUB</a> | <a href=\"\">MOBI</a> | <a href=\"\">3-file Zip</a></strong></p></div>",
            null,
            null,
            null,
            null,
            null,
            Identifier(
                "PrimaryID",
                "URI",
                "http://beyondgoodandevil.pressbooks.com"
            ),
            null,
            "en",
            null,
            null,
            null,
            expectedExtraMeta
        )
        epubStream.getOpf()
            .map { it.metaData }
            .test()
            .assertValue {
                it.toString().equals(expectedMetadata.toString())
            }
    }

    @Test
    fun getPackageManifestTest() {
        val expectedManifest = Manifest(
            arrayOf(
                Item("front-cover", "./test/OEBPS/front-cover.html", "application/xhtml+xml"),
                Item("title-page", "./test/OEBPS/title-page.html", "application/xhtml+xml"),
                Item("copyright", "./test/OEBPS/copyright.html", "application/xhtml+xml"),
                Item("table-of-contents", "./test/OEBPS/table-of-contents.html", "application/xhtml+xml"),
                Item("front-matter-001", "./test/OEBPS/front-matter-001-preface.html", "application/xhtml+xml"),
                Item("pressbooks-promo", "./test/OEBPS/pressbooks-promo.html", "application/xhtml+xml"),
                Item("chapter-001", "./test/OEBPS/chapter-001-chapter-i.html", "application/xhtml+xml"),
                Item("chapter-002", "./test/OEBPS/chapter-002-chapter-ii.html", "application/xhtml+xml"),
                Item("chapter-003", "./test/OEBPS/chapter-003-chapter-iii.html", "application/xhtml+xml"),
                Item("chapter-004", "./test/OEBPS/chapter-004-chapter-iv.html", "application/xhtml+xml"),
                Item("chapter-005", "./test/OEBPS/chapter-005-chapter-v.html", "application/xhtml+xml"),
                Item("chapter-006", "./test/OEBPS/chapter-006-chapter-vi.html", "application/xhtml+xml"),
                Item("chapter-007", "./test/OEBPS/chapter-007-chapter-vii.html", "application/xhtml+xml"),
                Item("chapter-008", "./test/OEBPS/chapter-008-chapter-viii.html", "application/xhtml+xml"),
                Item("chapter-009", "./test/OEBPS/chapter-009-chapter-ix.html", "application/xhtml+xml"),
                Item("back-matter-001", "./test/OEBPS/back-matter-001-from-the-heights.html", "application/xhtml+xml"),
                Item("media-Lato-Black", "./test/OEBPS/assets/Lato-Black.ttf", "application/x-font-ttf"),
                Item("media-Lato-BlackItalic", "./test/OEBPS/assets/Lato-BlackItalic.ttf", "application/x-font-ttf"),
                Item("media-Lato-Bold", "./test/OEBPS/assets/Lato-Bold.ttf", "application/x-font-ttf"),
                Item("media-Lato-BoldItalic", "./test/OEBPS/assets/Lato-BoldItalic.ttf", "application/x-font-ttf"),
                Item("media-Lato-Italic", "./test/OEBPS/assets/Lato-Italic.ttf", "application/x-font-ttf"),
                Item("media-Lato-Light", "./test/OEBPS/assets/Lato-Light.ttf", "application/x-font-ttf"),
                Item("media-Lato-LightItalic", "./test/OEBPS/assets/Lato-LightItalic.ttf", "application/x-font-ttf"),
                Item("media-Lato-Regular", "./test/OEBPS/assets/Lato-Regular.ttf", "application/x-font-ttf"),
                Item("cover-image", "./test/OEBPS/assets/beyondgoogandevil1.jpg", "image/jpeg"),
                Item("media-pressbooks-promo", "./test/OEBPS/assets/pressbooks-promo.png", "image/png"),
                Item("ncx", "./test/toc.ncx", "application/x-dtbncx+xml"),
                Item("stylesheet", "./test/OEBPS/galbraith-color.css", "text/css")
            )
        )
        epubStream.getOpf()
            .map { it.manifest }
            .test()
            .assertValue {
                it.items contentEquals expectedManifest.items
            }
    }

    @Test
    fun getPackageSpineTest() {
        val exptectedSpine = Spine(
            "ncx",
            arrayOf(
                ItemRef("front-cover", "no"),
                ItemRef("title-page", "yes"),
                ItemRef("copyright", "yes"),
                ItemRef("table-of-contents", "yes"),
                ItemRef("front-matter-001", "yes"),
                ItemRef("pressbooks-promo", "yes"),
                ItemRef("chapter-001", "yes"),
                ItemRef("chapter-002", "yes"),
                ItemRef("chapter-003", "yes"),
                ItemRef("chapter-004", "yes"),
                ItemRef("chapter-005", "yes"),
                ItemRef("chapter-006", "yes"),
                ItemRef("chapter-007", "yes"),
                ItemRef("chapter-008", "yes"),
                ItemRef("chapter-009", "yes"),
                ItemRef("back-matter-001", "yes")
            )
        )
        epubStream.getOpf()
            .map { it.spine }
            .test()
            .assertValue {
                it.itemrefs contentEquals exptectedSpine.itemrefs
            }
    }
}