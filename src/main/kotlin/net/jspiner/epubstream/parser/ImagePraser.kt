package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.Image
import net.jspiner.epubstream.getProperty
import org.w3c.dom.Node

fun parseImage(node: Node?): Image? {
    if (node == null) return null

    return Image(
        node.getProperty("id"),
        node.getProperty("src")!!
    )
}