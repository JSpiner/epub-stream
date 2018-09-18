package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.Package
import net.jspiner.epubstream.evaluateNode
import net.jspiner.epubstream.getProperty
import org.w3c.dom.Document

fun parsePackage(document: Document): Package {
    val packageNode = evaluateNode(document, "/package")!!
    return Package(
            packageNode.getProperty("version")!!,
            packageNode.getProperty("unique-identifier")!!,
            parseMetadata(document),
            parseManifest(document),
            parseSpine(document),
            parseGuide(document)
    )
}