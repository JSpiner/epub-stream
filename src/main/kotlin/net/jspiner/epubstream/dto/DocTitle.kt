package net.jspiner.epubstream.dto

// spec : http://www.daisy.org/z3986/2005/ncx/
data class DocTitle(
        var id: String?,
        var text: String,
        var img: Image?
)