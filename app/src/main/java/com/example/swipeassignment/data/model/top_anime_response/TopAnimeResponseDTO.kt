package com.example.swipeassignment.data.model.top_anime_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopAnimeResponseDTO(

    @SerialName("pagination") var pagination: Pagination? = Pagination(),
    @SerialName("data") var data: ArrayList<Data> = arrayListOf()

)

@Serializable
data class Items(

    @SerialName("count") var count: Int? = null,
    @SerialName("total") var total: Int? = null,
    @SerialName("per_page") var perPage: Int? = null

)

@Serializable
data class Pagination(

    @SerialName("last_visible_page") var lastVisiblePage: Int? = null,
    @SerialName("has_next_page") var hasNextPage: Boolean? = null,
    @SerialName("current_page") var currentPage: Int? = null,
    @SerialName("items") var items: Items? = Items()

)

@Serializable
data class Jpg(

    @SerialName("image_url") var imageUrl: String? = null,
    @SerialName("small_image_url") var smallImageUrl: String? = null,
    @SerialName("large_image_url") var largeImageUrl: String? = null

)

@Serializable
data class Webp(

    @SerialName("image_url") var imageUrl: String? = null,
    @SerialName("small_image_url") var smallImageUrl: String? = null,
    @SerialName("large_image_url") var largeImageUrl: String? = null

)

@Serializable
data class Images(

    @SerialName("jpg") var jpg: Jpg? = Jpg(),
    @SerialName("webp") var webp: Webp? = Webp()

)

@Serializable
data class ImagesTrailer(

    @SerialName("image_url") var imageUrl: String? = null,
    @SerialName("small_image_url") var smallImageUrl: String? = null,
    @SerialName("medium_image_url") var mediumImageUrl: String? = null,
    @SerialName("large_image_url") var largeImageUrl: String? = null,
    @SerialName("maximum_image_url") var maximumImageUrl: String? = null

)

@Serializable
data class Trailer(

    @SerialName("youtube_id") var youtubeId: String? = null,
    @SerialName("url") var url: String? = null,
    @SerialName("embed_url") var embedUrl: String? = null,
    @SerialName("images") var images: ImagesTrailer? = ImagesTrailer()

)

@Serializable
data class Titles(

    @SerialName("type") var type: String? = null,
    @SerialName("title") var title: String? = null

)

@Serializable
data class From(

    @SerialName("day") var day: Int? = null,
    @SerialName("month") var month: Int? = null,
    @SerialName("year") var year: Int? = null

)

@Serializable
data class To(

    @SerialName("day") var day: Int? = null,
    @SerialName("month") var month: Int? = null,
    @SerialName("year") var year: Int? = null

)

@Serializable
data class Prop(

    @SerialName("from") var from: From? = From(),
    @SerialName("to") var to: To? = To()

)

@Serializable
data class Aired(

    @SerialName("from") var from: String? = null,
    @SerialName("to") var to: String? = null,
    @SerialName("prop") var prop: Prop? = Prop(),
    @SerialName("string") var string: String? = null

)

@Serializable
data class Broadcast(

    @SerialName("day") var day: String? = null,
    @SerialName("time") var time: String? = null,
    @SerialName("timezone") var timezone: String? = null,
    @SerialName("string") var string: String? = null

)

@Serializable
data class Producers(

    @SerialName("mal_id") var malId: Int? = null,
    @SerialName("type") var type: String? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("url") var url: String? = null

)

@Serializable
data class Licensors(

    @SerialName("mal_id") var malId: Int? = null,
    @SerialName("type") var type: String? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("url") var url: String? = null

)

@Serializable
data class Studios(

    @SerialName("mal_id") var malId: Int? = null,
    @SerialName("type") var type: String? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("url") var url: String? = null

)

@Serializable
data class Genres(

    @SerialName("mal_id") var malId: Int? = null,
    @SerialName("type") var type: String? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("url") var url: String? = null

)

@Serializable
data class Demographics(

    @SerialName("mal_id") var malId: Int? = null,
    @SerialName("type") var type: String? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("url") var url: String? = null

)

@Serializable
data class Data(

    @SerialName("mal_id") var malId: Int? = null,
    @SerialName("url") var url: String? = null,
    @SerialName("images") var images: Images? = Images(),
    @SerialName("trailer") var trailer: Trailer? = Trailer(),
    @SerialName("approved") var approved: Boolean? = null,
    @SerialName("titles") var titles: ArrayList<Titles> = arrayListOf(),
    @SerialName("title") var title: String? = null,
    @SerialName("title_english") var titleEnglish: String? = null,
    @SerialName("title_japanese") var titleJapanese: String? = null,
    @SerialName("title_synonyms") var titleSynonyms: ArrayList<String> = arrayListOf(),
    @SerialName("type") var type: String? = null,
    @SerialName("source") var source: String? = null,
    @SerialName("episodes") var episodes: Int? = null,
    @SerialName("status") var status: String? = null,
    @SerialName("airing") var airing: Boolean? = null,
    @SerialName("aired") var aired: Aired? = Aired(),
    @SerialName("duration") var duration: String? = null,
    @SerialName("rating") var rating: String? = null,
    @SerialName("score") var score: Double? = null,
    @SerialName("scored_by") var scoredBy: Int? = null,
    @SerialName("rank") var rank: Int? = null,
    @SerialName("popularity") var popularity: Int? = null,
    @SerialName("members") var members: Int? = null,
    @SerialName("favorites") var favorites: Int? = null,
    @SerialName("synopsis") var synopsis: String? = null,
    @SerialName("background") var background: String? = null,
    @SerialName("season") var season: String? = null,
    @SerialName("year") var year: Int? = null,
    @SerialName("broadcast") var broadcast: Broadcast? = Broadcast(),
    @SerialName("producers") var producers: ArrayList<Producers> = arrayListOf(),
    @SerialName("licensors") var licensors: ArrayList<Licensors> = arrayListOf(),
    @SerialName("studios") var studios: ArrayList<Studios> = arrayListOf(),
    @SerialName("genres") var genres: ArrayList<Genres> = arrayListOf(),
    @SerialName("explicit_genres") var explicitGenres: ArrayList<String> = arrayListOf(),
//    @SerialName("themes") var themes: ArrayList<String> = arrayListOf(),
    @SerialName("demographics") var demographics: ArrayList<Demographics> = arrayListOf()

)