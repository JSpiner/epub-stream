package net.jspiner.epubstream.dto

// spec : http://www.daisy.org/z3986/2005/ncx/
data class Meta(
        var name: String,
        var content: String,
        var scheme: String?
)

data class Head(
        var metas: Array<Meta>
)