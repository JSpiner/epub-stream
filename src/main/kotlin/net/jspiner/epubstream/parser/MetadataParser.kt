package net.jspiner.epubstream.parser

import net.jspiner.epubstream.dto.MetaData
import net.jspiner.epubstream.evaluateNode
import net.jspiner.epubstream.evaluateNodeList
import net.jspiner.epubstream.getProperty
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun parseMetadata(document: Document): MetaData {
    val META_PATH = "/package/metadata/"
    fun getChildNode(nodeName: String): Node? = evaluateNode(document, "$META_PATH$nodeName")
    fun getChildNodeList(nodeName: String): NodeList? = evaluateNodeList(document, "$META_PATH$nodeName")

    return MetaData(
            getChildNode("title")!!.textContent,
            parseCreator(getChildNode("creator")),
            parseSubject(getChildNodeList("subject")),
            getChildNode("description")?.textContent,
            getChildNode("publisher")?.textContent,
            parseCreator(getChildNode("contributor")),
            parseDate(getChildNode("date")?.textContent),
            getChildNode("type")?.textContent,
            getChildNode("fotmat")?.textContent,
            parseIdentifier(getChildNode("identifier")!!),
            getChildNode("source")?.textContent,
            getChildNode("language")!!.textContent,
            getChildNode("releation")?.textContent,
            getChildNode("coverage")?.textContent,
            getChildNode("rights")?.textContent,
            parseMeta(document)
    )
}

fun parseDate(date: String?): Date? {
    if (date == null) return null

    val ableFormats = arrayListOf(
            "yyyy",
            "yyyy-MM",
            "yyyy-MM-dd"
    )
    for (format in ableFormats) {
        try {
            return SimpleDateFormat(format).parse(date)
        } catch (_: ParseException) {
            //no-op
        }
    }
    throw ParseException(date, 0)
}

fun parseMeta(document: Document): HashMap<String, String> {
    val nodeList = evaluateNodeList(document, "/package/metadata/meta")
    val hashMap = HashMap<String, String>()
    for (i in 0 until nodeList.length) {
        hashMap.put(
                nodeList.item(i).getProperty("name")?: nodeList.item(i).getProperty("property")!!,
                nodeList.item(i).getProperty("content")?: nodeList.item(i).nodeValue
        )
    }
    return hashMap
}