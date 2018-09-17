package dto

//spec : http://www.idpf.org/epub/20/spec/OPF_2.0.1_draft.htm#Section2.2.10
data class Identifier(
        var id:String,
        var scheme:String?,
        var identifier: String
)