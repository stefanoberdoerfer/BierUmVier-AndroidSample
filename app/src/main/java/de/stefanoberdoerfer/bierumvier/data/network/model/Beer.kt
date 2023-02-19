package de.stefanoberdoerfer.bierumvier.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Beer(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String? = null,
    @Json(name = "tagline") val tagline: String? = null,
    @Json(name = "first_brewed") val firstBrewed: String? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "image_url") val imageUrl: String? = null,
    @Json(name = "abv") val abv: Double? = null,
    @Json(name = "ibu") val ibu: Double? = null,
    @Json(name = "target_fg") val targetFg: Double? = null,
    @Json(name = "target_og") val targetOg: Double? = null,
    @Json(name = "ebc") val ebc: Double? = null,
    @Json(name = "srm") val srm: Double? = null,
    @Json(name = "ph") val ph: Double? = null,
    @Json(name = "attenuation_level") val attenuationLevel: Double? = null,
    @Json(name = "volume") val volume: Amount? = Amount(),
    @Json(name = "boil_volume") val boilVolume: Amount? = Amount(),
    @Json(name = "ingredients") val ingredients: Ingredients? = Ingredients(),
    @Json(name = "food_pairing") val foodPairing: List<String> = listOf(),
    @Json(name = "brewers_tips") val brewersTips: String? = null,
    @Json(name = "contributed_by") val contributedBy: String? = null
)

@JsonClass(generateAdapter = true)
data class Ingredients(
    @Json(name = "malt") var malt: List<Ingredient> = listOf(),
    @Json(name = "hops") var hops: List<Ingredient> = listOf(),
    @Json(name = "yeast") var yeast: String? = null
)

@JsonClass(generateAdapter = true)
data class Ingredient(
    @Json(name = "name") var name: String? = null,
    @Json(name = "amount") var amount: Amount? = Amount(),
    @Json(name = "add") var add: String? = null,
    @Json(name = "attribute") var attribute: String? = null
)

@JsonClass(generateAdapter = true)
data class Amount(
    @Json(name = "value") var value: Double? = null,
    @Json(name = "unit") var unit: String? = null
)