package com.ucfjoe.marvelcomics.feature_marvel.data.remote.dto

data class SeriesList(
    val available: Int?,
    val collectionURI: String?,
    val items: List<SeriesSummary>?,
    val returned: Int?
)