package net.jspiner.epubstream.dto

// spec : http://www.daisy.org/z3986/2005/ncx/
data class NavMap(
        var id: String?,
        var navPoints: Array<NavPoint>
)