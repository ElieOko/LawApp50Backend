package emy.backend.lawapp50.app.ouvrages.infrastructure.persistance.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "ouvrages")
data class OuvrageEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id      : Long? = null,

    @Column(name = "auteur_id", nullable = false)
    val authId  : Long,

    @Column(name = "titre", nullable = false)
    val titre   : String,

    @Column(name = "langue", nullable = false)
    val langue  : String? = null,

    @Column(name = "isbn")
    val isbn    : String? = null,

    @Column(name = "n_page")
    val nPage   : Long? = null,

    @Column(name = "genre", nullable = false)
    val genre   : String,

    @Column(name = "type_ouvrage", nullable = false)
    val typeOuvrage : String,

    @Column(name = "roman")
    val roman      : String?,

    @Column(name = "type_revue")
    val typeRevue   : String?,

    @Column(name = "type_article")
    val typeArticle : String?,

    @Column(name = "public_path")
    val publicPath  : String?,

    @Column(name = "date_publication", nullable = false)
    val datePublication: LocalDate
)