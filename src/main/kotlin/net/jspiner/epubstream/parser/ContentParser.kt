package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.Content
import net.jspiner.epubstream.getProperty
import org.w3c.dom.Node
import java.io.File

fun parseContent(node: Node, tocPath: String): Content {
    var src = node.getProperty("src")!!
    if (src.startsWith("/").not()) {
        src = tocPath + File.separator + src
    }
    return Content(
        node.getProperty("id"),
        src
    )
}