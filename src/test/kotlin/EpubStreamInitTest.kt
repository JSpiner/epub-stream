import org.junit.Test
import java.io.File

@Test
fun initSuccessTest() {
    val dummyFile = File("file://dummy/file")
    EpubStream(dummyFile)
}