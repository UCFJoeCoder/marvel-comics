package com.ucfjoe.marvelcomics.feature_marvel.domain.model

data class Character(
    val characterId: String,
    val name: String,
    val description: String?,
    val thumbnail: String,
    val comicsCounts: Int,
    val storiesCount: Int,
    val eventsCount: Int,
    val seriesCount: Int
)
