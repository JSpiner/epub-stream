package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.NavMap
import net.jspiner.epubstream.evaluateNode
import net.jspiner.epubstream.evaluateNodeList
import net.jspiner.epubstream.getProperty
import org.w3c.dom.Document

fun parseNavMap(document: Document): NavMap {
    val node = evaluateNode(document, "/ncx/navMap")!!
    val nodeList = evaluateNodeList(document, "/ncx/navMap/navPoint")!!
    return NavMap(
            node.getProperty("id"),
            parseNavPoints(nodeList)
    )
}