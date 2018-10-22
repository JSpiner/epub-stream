package net.jspiner.epubstream.dto

import java.io.Serializable

// spec : http://www.daisy.org/z3986/2005/ncx/
data class NavPoint(
        var id: String,
        var playOrder: String,
        var navLabel: NavLabel,
        var content: Content
) : Serializable