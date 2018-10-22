package net.jspiner.epubstream.dto

import java.io.Serializable

// spec : http://www.idpf.org/epub/20/spec/OPF_2.0.1_draft.htm#Section2.6
data class Guide(
        var references: Array<Reference>
) : Serializable

data class Reference(
        var type: String?,
        var title: String?,
        var href: String
) : Serializable