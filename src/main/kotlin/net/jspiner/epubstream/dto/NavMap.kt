package net.jspiner.epubstream.dto

import java.io.Serializable

// spec : http://www.daisy.org/z3986/2005/ncx/
data class NavMap(
    var id: String?,
    var navPoints: Array<NavPoint>
) : Serializable