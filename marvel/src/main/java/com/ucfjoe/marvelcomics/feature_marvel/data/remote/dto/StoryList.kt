package com.ucfjoe.marvelcomics.feature_marvel.data.remote.dto

data class StoryList(
    val available: Int?,
    val collectionURI: String?,
    val items: List<StorySummary>?,
    val returned: Int?
)