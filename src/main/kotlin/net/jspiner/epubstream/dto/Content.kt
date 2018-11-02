package net.jspiner.epubstream.dto

import java.io.Serializable

// spec : http://www.daisy.org/z3986/2005/ncx/
data class Content(
    var id: String?,
    var src: String
) : Serializable