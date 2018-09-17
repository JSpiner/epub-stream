package net.jspiner.epubstream.dto

import java.util.*
import kotlin.collections.HashMap

//spec : http://dublincore.org/documents/2004/12/20/dces/
data class MetaData(
        var title: String,
        var creator: Creator?,
        var subject: Array<String>?,
        var description: String?,
        var publisher: String?,
        var contributor: Creator?,
        var date: Date?, // YYYY[-MM[-DD]]: 4자리 연도(필수) 2자리 월(선택) 2자리 일(선택)
        var type: String?,
        var format: String?,
        var identifier: Identifier,
        var source: String?,
        var language: String,
        var relation: String?,
        var coverage: String?,
        var rights: String?,
        var meta: HashMap<String, String>?
)