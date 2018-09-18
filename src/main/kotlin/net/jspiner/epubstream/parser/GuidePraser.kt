package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.Guide
import net.jspiner.epubstream.dto.Reference
import net.jspiner.epubstream.evaluateNodeList
import net.jspiner.epubstream.getProperty
import org.w3c.dom.Document

fun parseGuide(document: Document): Guide? {
    val nodeList = evaluateNodeList(document, "/package/guide/reference")
    val items = Array(nodeList.length) {
        val node = nodeList.item(it)
        return@Array Reference(
                node.getProperty("type"),
                node.getProperty("title"),
                node.getProperty("href")!!
        )
    }

    return Guide(items)
}