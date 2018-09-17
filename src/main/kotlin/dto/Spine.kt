package dto

// spec : http://www.idpf.org/epub/20/spec/OPF_2.0.1_draft.htm#Section2.4
data class Spine(
        var toc: String,
        var itemrefs: Array<ItemRef>
)

data class ItemRef(
        var idRef: String,
        var linear: String = "yes"
)