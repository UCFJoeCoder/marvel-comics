package com.ucfjoe.marvelcomics.feature_marvel.data.remote.dto

data class ComicList(
    val available: Int?,
    val collectionURI: String?,
    val items: List<ComicSummary>?,
    val returned: Int?
)