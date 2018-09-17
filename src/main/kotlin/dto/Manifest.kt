package dto

//spec : http://www.idpf.org/epub/20/spec/OPF_2.0.1_draft.htm#Section2.3
data class Manifest(
    var items: Array<Item>
)

data class Item(
        var id:String,
        var href:String,
        var mediaType: String
)