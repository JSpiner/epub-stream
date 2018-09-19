package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.Head
import net.jspiner.epubstream.dto.Meta
import net.jspiner.epubstream.getProperty
import org.w3c.dom.NodeList

fun parseHead(nodeList: NodeList): Head {
    val metas = Array(nodeList.length) {
        val node = nodeList.item(it)
        Meta(
                node.getProperty("name")!!,
                node.getProperty("content")!!,
                node.getProperty("scheme")
        )
    }
    return Head(metas)
}