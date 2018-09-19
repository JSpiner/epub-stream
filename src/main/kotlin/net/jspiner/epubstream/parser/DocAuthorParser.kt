package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.DocAuthor
import net.jspiner.epubstream.getChildeNode
import net.jspiner.epubstream.getProperty
import org.w3c.dom.Node

fun parseDocAuthor(node: Node?): DocAuthor? {
    if (node == null) return null

    return DocAuthor(
            node.getProperty("id"),
            node.childNodes.getChildeNode("text")!!.textContent,
            parseImage(node.childNodes.getChildeNode("img"))
    )
}