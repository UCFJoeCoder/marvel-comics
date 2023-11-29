package com.ucfjoe.marvelcomics.feature_marvel.data.remote.dto

data class ComicDataContainer(
    val count: Int?,
    val limit: Int?,
    val offset: Int?,
    val results: List<Comic>?,
    val total: Int?
)