package net.jspiner.epubstream

import net.jspiner.epubstream.EpubStream
import org.junit.Test
import java.io.File

class EpubStreamInitTest {

    @Test
    fun initSuccessTest() {
        val dummyFile = File("file://dummy/file")
        EpubStream(dummyFile)
    }

    @Test
    fun getContainerSuccessTest() {
        val dummyFile = File("file://dummy/file")
        val epubStream = EpubStream(dummyFile)
        epubStream.getContainer()
                .test()
                .assertResult(1)
    }

    @Test
    fun getOpfSuccessTest() {
        val dummyFile = File("file://dummy/file")
        val epubStream = EpubStream(dummyFile)
        epubStream.getOpf()
                .test()
                .assertResult(1)
    }

    @Test
    fun getTocSuccessTest() {
        val dummyFile = File("file://dummy/file")
        val epubStream = EpubStream(dummyFile)
        epubStream.getToc()
                .test()
                .assertResult(1)
    }

    @Test
    fun getFileSuccessTest() {
        val dummyFile = File("file://dummy/file")
        val epubStream = EpubStream(dummyFile)
        epubStream.getFile()
                .test()
                .assertResult(1)
    }

}