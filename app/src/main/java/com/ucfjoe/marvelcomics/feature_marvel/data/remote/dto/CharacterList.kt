package com.ucfjoe.marvelcomics.feature_marvel.data.remote.dto

data class CharacterList(
    val available: Int?,
    val collectionURI: String?,
    val items: List<CharacterSummary>?,
    val returned: Int?
)