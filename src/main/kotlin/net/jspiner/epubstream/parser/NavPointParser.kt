package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.NavPoint
import net.jspiner.epubstream.getChildeNode
import net.jspiner.epubstream.getProperty
import org.w3c.dom.Node
import org.w3c.dom.NodeList

fun parseNavPoints(nodeList: NodeList): Array<NavPoint> {
    return Array(nodeList.length) {
        val node = nodeList.item(it)
        parseNavPoint(node)
    }
}

fun parseNavPoint(node: Node): NavPoint {
    return NavPoint(
            node.getProperty("id")!!,
            node.getProperty("playOrder")!!,
            parseNavLabel(node.childNodes.getChildeNode("navLabel")!!),
            parseContent(node.childNodes.getChildeNode("content")!!)
    )
}