package net.jspiner.epubstream.dto

//spec : http://www.idpf.org/epub/30/spec/epub30-ocf.html
data class RootFile(
        var fullPath : String,
        var mediaType: String
)