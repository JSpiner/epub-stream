package dto

//spec : http://www.idpf.org/epub/20/spec/OPF_2.0.1_draft.htm#Section2.2.6
enum class RoleType {
    adp,    // Adapter
    ann,    // Annotator
    arr,    // Arranger
    art,    // Artist
    asn,    // Associated name
    aut,    // Author
    aqt,    // Author in quotations or text extracts
    aft,    // Author of afterword, colophon, etc.
    aui,    // Author of introduction, etc.
    ant,    // Bibliographic antecedent
    bkp,    // Book producer
    clb,    // Collaborator
    cmm,    // Commentator
    dsr,    // Designer
    edt,    // Editor
    ill,    // Illustrator
    lyr,    // Lyricist
    mdc,    // Metadata contact
    mus,    // Musician
    nrt,    // Narrator
    oth,    // Other
    pht,    // Photographer
    prt,    // Printer
    red,    // Redactor
    rev,    // Reviewer
    spn,    // Sponsor
    ths,    // Thesis advisor
    trc,    // Transcriber
    trl,    // Translator
}