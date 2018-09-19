package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.Creator
import net.jspiner.epubstream.dto.RoleType
import net.jspiner.epubstream.getProperty
import org.w3c.dom.Node

fun parseCreator(node: Node?): Creator? {
    if (node == null) return null

    val role = node.getProperty("opf:role")
    return Creator(
            node.getProperty("opf:file-as"),
            if (role != null) RoleType.valueOf(role) else null,
            node.textContent
    )
}