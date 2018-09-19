package net.jspiner.epubstream.dto

// spec : http://www.daisy.org/z3986/2005/ncx/
data class Audio(
        var id: String?,
        var src: String,
        var clipBegin: String,
        var clipEnd: String
)