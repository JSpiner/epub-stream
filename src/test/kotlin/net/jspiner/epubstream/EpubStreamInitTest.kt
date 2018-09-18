package net.jspiner.epubstream

import org.junit.Test
import java.io.File

class EpubStreamInitTest {

    @Test
    fun initSuccessTest() {
        val dummyFile = File("file://dummy/file")
        EpubStream(dummyFile)
    }
}