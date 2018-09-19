package net.jspiner.epubstream

import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.File
import java.io.FileReader
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory


fun parseToDocument(file: File) : Document {
    val inputSource = InputSource(FileReader(file))
    return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource)
}

fun evaluateNodeList(document: Document, expression: String): NodeList {
    val xPath = XPathFactory.newInstance().newXPath()
    return xPath.compile(expression).evaluate(document, XPathConstants.NODESET) as NodeList
}

fun evaluateNode(document: Document, expression: String): Node? {
    val xPath = XPathFactory.newInstance().newXPath()
    return xPath.compile(expression).evaluate(document, XPathConstants.NODE) as? Node
}

fun Node.getProperty(propertyName: String) : String? {
    return this.attributes.getNamedItem(propertyName)?.nodeValue
}