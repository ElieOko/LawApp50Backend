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

object CategorieContenuScope{
    const val PUBLIC = "${GlobalRoute.PUBLIC}/${FeatureActor.CATEGORICONTENU}"
    const val PROTECTED = "${GlobalRoute.PROTECT}/${FeatureActor.CATEGORICONTENU}"
    const val PRIVATE = "${GlobalRoute.PRIVATE}/${FeatureActor.CATEGORICONTENU}"
}


object CategorieScope{
    const val PUBLIC = "${GlobalRoute.PUBLIC}/${FeatureActor.CATEGORIE}"
    const val PROTECTED = "${GlobalRoute.PROTECT}/${FeatureActor.CATEGORIE}"
    const val PRIVATE = "${GlobalRoute.PRIVATE}/${FeatureActor.CATEGORIE}"
}
object CommentaireScope{
    const val PUBLIC = "${GlobalRoute.PUBLIC}/${FeatureActor.COMMENTAIRE}"
    const val PROTECTED = "${GlobalRoute.PROTECT}/${FeatureActor.COMMENTAIRE}"
    const val PRIVATE = "${GlobalRoute.PRIVATE}/${FeatureActor.COMMENTAIRE}"
}
object ResponseScope{
    const val PUBLIC = "${GlobalRoute.PUBLIC}/${FeatureActor.RESPONSECOM}"
    const val PROTECTED = "${GlobalRoute.PROTECT}/${FeatureActor.RESPONSECOM}"
    const val PRIVATE = "${GlobalRoute.PRIVATE}/${FeatureActor.RESPONSECOM}"
}

object FavoriScope{
    const val PUBLIC = "${GlobalRoute.PUBLIC}/${FeatureActor.FAVORI}"
    const val PROTECTED = "${GlobalRoute.PROTECT}/${FeatureActor.FAVORI}"
    const val PRIVATE = "${GlobalRoute.PRIVATE}/${FeatureActor.FAVORI}"
}


object FeatureActor{
    const val CONTENU = "contenu"
    const val AVIS = "avis"
    const val CATEGORICONTENU ="categoriecontenu"
    const val CATEGORIE = "categorie"
    const val COMMENTAIRE = "commentaire"
    const val RESPONSECOM = "response"
    const val FAVORI = "favori"
}