package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.DocTitle
import net.jspiner.epubstream.getChildNode
import net.jspiner.epubstream.getProperty
import org.w3c.dom.Node

fun parseDocTitle(node: Node): DocTitle {
    return DocTitle(
            node.getProperty("id"),
            node.childNodes.getChildNode("text")!!.textContent,
            parseImage(node.childNodes.getChildNode("img"))
    )
}