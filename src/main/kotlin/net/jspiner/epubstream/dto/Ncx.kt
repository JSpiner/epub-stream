package net.jspiner.epubstream.dto

import java.io.Serializable

// spec : http://www.daisy.org/z3986/2005/ncx/
data class Ncx(
        var head: Head,
        var docTitle: DocTitle,
        var docAuthor: DocAuthor?,
        var navMap: NavMap
) : Serializable