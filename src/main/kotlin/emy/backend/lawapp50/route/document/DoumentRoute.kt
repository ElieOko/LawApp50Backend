package emy.backend.lawapp50.route.document

import emy.backend.lawapp50.route.GlobalRoute

object DocumentScope{
    const val PUBLIC = "${GlobalRoute.PUBLIC}/${DocumentFeatures.DOCUMENT}"
    const val PROTECTED = "${GlobalRoute.PROTECT}/${DocumentFeatures.DOCUMENT}"
    const val PRIVATE = "${GlobalRoute.PRIVATE}/${DocumentFeatures.DOCUMENT}"
}

object AVISDocumentScope{
    const val PUBLIC = "${GlobalRoute.PUBLIC}/${DocumentFeatures.AVISDOC}"
    const val PROTECTED = "${GlobalRoute.PROTECT}/${DocumentFeatures.AVISDOC}"
    const val PRIVATE = "${GlobalRoute.PRIVATE}/${DocumentFeatures.AVISDOC}"
}

object CATDocumentScope{
    const val PUBLIC = "${GlobalRoute.PUBLIC}/${DocumentFeatures.CATDOC}"
    const val PROTECTED = "${GlobalRoute.PROTECT}/${DocumentFeatures.CATDOC}"
    const val PRIVATE = "${GlobalRoute.PRIVATE}/${DocumentFeatures.CATDOC}"
}

object TYPEDocumentScope{
    const val PUBLIC = "${GlobalRoute.PUBLIC}/${DocumentFeatures.TYPEDOC}"
    const val PROTECTED = "${GlobalRoute.PROTECT}/${DocumentFeatures.TYPEDOC}"
    const val PRIVATE = "${GlobalRoute.PRIVATE}/${DocumentFeatures.TYPEDOC}"
}

object DocumentFeatures {
    const val DOCUMENT = "document"
    const val AVISDOC = "avis-document"
    const val CATDOC = "cat-document"
    const val TYPEDOC = "type-document"
}