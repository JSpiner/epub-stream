package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.Ncx
import net.jspiner.epubstream.evaluateNode
import net.jspiner.epubstream.evaluateNodeList
import org.w3c.dom.Document

fun parseNcx(document: Document, tocPath: String) : Ncx {
    return Ncx(
            parseHead(evaluateNodeList(document, "/ncx/head/meta")),
            parseDocTitle(evaluateNode(document, "/ncx/docTitle")!!),
            parseDocAuthor(evaluateNode(document, "/ncx/docAuthor")),
            parseNavMap(document, tocPath)
    )
}