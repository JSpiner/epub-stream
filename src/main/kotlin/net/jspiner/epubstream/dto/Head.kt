package net.jspiner.epubstream.dto

import java.io.Serializable

// spec : http://www.daisy.org/z3986/2005/ncx/
data class Meta(
        var name: String,
        var content: String,
        var scheme: String?
) : Serializable

data class Head(
        var metas: Array<Meta>
) : Serializable