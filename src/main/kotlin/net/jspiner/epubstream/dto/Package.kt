package net.jspiner.epubstream.dto

import java.io.Serializable

// spec : http://www.idpf.org/epub/20/spec/OPF_2.0.1_draft.htm
data class Package(
        var version: String,
        var uniqueIdentifier: String,
        var metaData: MetaData,
        var manifest: Manifest,
        var spine: Spine,
        var guide: Guide?
) : Serializable