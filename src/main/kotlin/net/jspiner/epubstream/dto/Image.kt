package net.jspiner.epubstream.dto

import java.io.Serializable

data class Image(
        var id: String?,
        var src: String
) : Serializable