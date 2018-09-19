package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.NavLabel
import net.jspiner.epubstream.getChildeNode
import org.w3c.dom.Node

fun parseNavLabel(node: Node): NavLabel {
    return NavLabel(
            node.childNodes.getChildeNode("text")!!.textContent,
            parseImage(node.childNodes.getChildeNode("img"))
    )
}