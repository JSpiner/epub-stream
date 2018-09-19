# epub-stream
[![Build Status](https://travis-ci.org/JSpiner/epub-stream.svg?branch=master)](https://travis-ci.org/JSpiner/epub-stream) [![Coverage Status](https://coveralls.io/repos/github/JSpiner/epub-stream/badge.svg?branch=3ad56644a54b89cc05024609cac9b082563deb28)](https://coveralls.io/github/JSpiner/epub-stream?branch=3ad56644a54b89cc05024609cac9b082563deb28)

epub-stream is rxjava-based epub parser

[bug? have a question?](https://github.com/JSpiner/epub-stream/issues)

## Basic Usage
```
- epub(unzip) 
    - mimetype
        - container
            - opf
            - metadata
            - manifest
            - spine
            - guide
            - toc
```
- It has the above object priority.
- When you request a child object, it automatically loads the parent object if necessary.
- Loaded objects are cached.

```kotlin
var epubStream = EpubStream(file)

//unzip
epubStream.unzip()                  //completable
        .subscribe { }

//getExtractedDirectory
epubStream.getExtractedDirectory()  //single
        .subscribe {file -> }      

//getMimeType
epubStream.getMimeType()            //single
        .subscribe {mimeType -> } 

//getContainer
epubStream.getContainer()           //single
        .subscribe {container -> }  

//getOpf
epubStream.getOpf()                 //single
        .subscribe {pacakge -> }


```
