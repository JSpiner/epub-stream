package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.ItemRef
import net.jspiner.epubstream.dto.Spine
import net.jspiner.epubstream.evaluateNode
import net.jspiner.epubstream.evaluateNodeList
import net.jspiner.epubstream.getProperty
import org.w3c.dom.Document

fun parseSpine(document: Document): Spine {
    val node = evaluateNode(document, "/package/spine")!!
    val nodeList = evaluateNodeList(document, "/package/spine/itemref")
    val items = Array(nodeList.length) {
        val childNode = nodeList.item(it)
        return@Array ItemRef(
                childNode.getProperty("idref")!!,
                childNode.getProperty("linear") ?: "yes"
        )
    }

    return Spine(
            node.getProperty("toc")!!,
            items
    )
}