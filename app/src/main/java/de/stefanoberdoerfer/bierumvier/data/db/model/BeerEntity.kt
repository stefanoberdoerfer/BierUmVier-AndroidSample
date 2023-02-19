package de.stefanoberdoerfer.bierumvier.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.stefanoberdoerfer.bierumvier.data.network.model.Beer

@Entity
data class BeerEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val imgUrl: String?,
    val tagline: String?,
    val abv: Float,
    val foodPairings: String,
    val brewersTips: String?,
    val contributedBy: String?,
    var evaluation: Float? = null
) {
    companion object {
        fun from(beer: Beer): BeerEntity =
            with(beer) {
                BeerEntity(
                    id,
                    name ?: "No name",
                    imageUrl,
                    tagline,
                    abv?.toFloat() ?: 0.0f,
                    foodPairing.joinToString(separator = "\n"),
                    brewersTips,
                    contributedBy
                )
            }
    }
}