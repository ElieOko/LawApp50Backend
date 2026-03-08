package emy.backend.lawapp50.route.contenu

import emy.backend.lawapp50.route.GlobalRoute

object ContenuScope{
    const val PUBLIC = "${GlobalRoute.PUBLIC}/${FeatureActor.CONTENU}"
    const val PROTECTED = "${GlobalRoute.PROTECT}/${FeatureActor.CONTENU}"
    const val PRIVATE = "${GlobalRoute.PRIVATE}/${FeatureActor.CONTENU}"
}

object AvisContenuScope{
    const val PUBLIC = "${GlobalRoute.PUBLIC}/${FeatureActor.AVIS}"
    const val PROTECTED = "${GlobalRoute.PROTECT}/${FeatureActor.AVIS}"
    const val PRIVATE = "${GlobalRoute.PRIVATE}/${FeatureActor.AVIS}"
}


object FeatureActor{
    const val CONTENU = "contenu"
    const val AVIS = "avis"
}