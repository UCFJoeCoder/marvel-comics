package com.ucfjoe.marvelcomics.feature_marvel.data.remote.dto

data class CreatorList(
    val available: Int?,
    val collectionURI: String?,
    val items: List<CreatorSummary>?,
    val returned: Int?
)