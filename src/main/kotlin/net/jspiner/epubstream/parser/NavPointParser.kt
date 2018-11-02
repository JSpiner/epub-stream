package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.NavPoint
import net.jspiner.epubstream.getChildNode
import net.jspiner.epubstream.getProperty
import org.w3c.dom.Node
import org.w3c.dom.NodeList

fun parseNavPoints(nodeList: NodeList, tocPath: String): Array<NavPoint> {
    return Array(nodeList.length) {
        val node = nodeList.item(it)
        parseNavPoint(node, tocPath)
    }
}

fun parseNavPoint(node: Node, tocPath: String): NavPoint {
    return NavPoint(
        node.getProperty("id")!!,
        node.getProperty("playOrder")!!,
        parseNavLabel(node.childNodes.getChildNode("navLabel")!!),
        parseContent(node.childNodes.getChildNode("content")!!, tocPath)
    )
}