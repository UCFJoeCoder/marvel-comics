package com.ucfjoe.marvelcomics.feature_marvel.data.remote.dto

data class EventList(
    val available: Int?,
    val collectionURI: String?,
    val items: List<EventSummary>?,
    val returned: Int?
)