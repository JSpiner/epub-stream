package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.NavLabel
import net.jspiner.epubstream.getChildNode
import org.w3c.dom.Node

fun parseNavLabel(node: Node): NavLabel {
    return NavLabel(
        node.childNodes.getChildNode("text")!!.textContent,
        parseImage(node.childNodes.getChildNode("img"))
    )
}