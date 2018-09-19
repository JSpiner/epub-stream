package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.Content
import net.jspiner.epubstream.getProperty
import org.w3c.dom.Node

fun parseContent(node: Node): Content {
    return Content(
            node.getProperty("id"),
            node.getProperty("src")!!
    )
}