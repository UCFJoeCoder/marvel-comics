package com.ucfjoe.marvelcomics.feature_marvel.data.remote.dto

data class CharacterDataWrapper(
    val attributionHTML: String?,
    val attributionText: String?,
    val code: Int?,
    val copyright: String?,
    val data: CharacterDataContainer?,
    val etag: String?,
    val status: String?
)