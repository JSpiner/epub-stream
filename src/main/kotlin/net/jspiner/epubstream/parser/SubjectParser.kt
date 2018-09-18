package net.jspiner.epubstream.parser

import org.w3c.dom.NodeList

fun parseSubject(nodeList: NodeList?): Array<String>? {
    if (nodeList == null) return null

    return Array(nodeList.length) {
        nodeList.item(it).textContent
    }
}