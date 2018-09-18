package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.Identifier
import net.jspiner.epubstream.getProperty
import org.w3c.dom.Node

fun parseIdentifier(node: Node): Identifier {
    return Identifier(
            node.getProperty("id")!!,
            node.getProperty("opf:scheme"),
            node.textContent
    )
}