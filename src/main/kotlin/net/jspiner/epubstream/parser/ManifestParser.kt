package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.Item
import net.jspiner.epubstream.dto.Manifest
import net.jspiner.epubstream.evaluateNodeList
import net.jspiner.epubstream.getProperty
import org.w3c.dom.Document

fun parseManifest(document: Document): Manifest {
    val nodeList = evaluateNodeList(document, "/package/manifest/item")
    val items = Array(nodeList.length) {
        val node = nodeList.item(it)
        return@Array Item(
                node.getProperty("id")!!,
                node.getProperty("href")!!,
                node.getProperty("media-type")!!
        )
    }

    return Manifest(items)
}