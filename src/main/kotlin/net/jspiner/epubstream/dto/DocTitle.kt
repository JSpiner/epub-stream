package net.jspiner.epubstream.dto

import java.io.Serializable

// spec : http://www.daisy.org/z3986/2005/ncx/
data class DocTitle(
    var id: String?,
    var text: String,
    var img: Image?
) : Serializable