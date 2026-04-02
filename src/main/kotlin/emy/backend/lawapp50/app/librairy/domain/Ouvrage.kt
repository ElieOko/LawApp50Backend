package emy.backend.lawapp50.app.librairy.domain

import emy.backend.lawapp50.app.librairy.enum.*
import java.time.LocalDate

data class Ouvrage(
    val id      : Long,
    val authId  : Long,
    val titre   : String,
    val langue  : String = Langue.FRANCAIS.name,
    val ISBN    : String? = null,
    //ISBN (International Standard Book Number)
    val nPage   : Long?,
    val genre   : String = Genre.SCIENTIFIQUE.name,
    val typeOuvrage : String = TypeOuvrage.ARTICLE.name,
    val roman       : String? = TypeRoman.HISTORIQUE.name,
    val typeRevue   : String? = TypeRevue.LITTERAIRE.name,
    val typeArticle : String? =TypeArticle.LITTERAIRE.name,
    val datePublication : LocalDate,
    val publicPath  : String?
)

data class OuvrageRequest(
    val authId  : Long,
    val titre   : String,
    val langue  : Langue,
    val ISBN    : String? = null,
    val nPage   : Long?,
    val genre   :  Genre,
    val typeOuvrage : TypeOuvrage,
    val roman       : TypeRoman,
    val typeRevue   : TypeRevue,
    val typeArticle : TypeArticle,
    val datePublication : LocalDate,
    val publicPath  : String?
)