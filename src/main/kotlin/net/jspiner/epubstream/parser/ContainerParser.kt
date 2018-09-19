package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.Container
import net.jspiner.epubstream.dto.RootFile
import net.jspiner.epubstream.evaluateNodeList
import net.jspiner.epubstream.getProperty
import org.w3c.dom.Document

fun parseContainer(document: Document): Container {
    val nodeList = evaluateNodeList(document, "/container/rootfiles/rootfile")

    val list = List(nodeList.length) { it -> nodeList.item(it) }
            .map { node ->
                RootFile(
                        node.getProperty("full-path")!!,
                        node.getProperty("media-type")!!
                )
            }
    return Container(list.toTypedArray())
}