package com.ucfjoe.marvelcomics.feature_marvel.data.remote.dto

import com.ucfjoe.marvelcomics.feature_marvel.data.remote.toCharacter

data class Character(
    val comics: ComicList? = null,
    val description: String?,
    val events: EventList? = null,
    val id: String,
    val modified: String? = "",
    val name: String?,
    val resourceURI: String? = "",
    val series: SeriesList? = null,
    val stories: StoryList? = null,
    val thumbnail: Image?,
    val urls: List<Url>? = emptyList()
) {
    fun getImagePath(): String = (thumbnail?.path ?: "") + "." + (thumbnail?.extension ?: "")
}