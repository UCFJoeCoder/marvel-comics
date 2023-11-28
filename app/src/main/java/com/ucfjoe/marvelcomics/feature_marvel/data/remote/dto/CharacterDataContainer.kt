package com.ucfjoe.marvelcomics.feature_marvel.data.remote.dto

data class CharacterDataContainer(
    val count: Int?,
    val limit: Int?,
    val offset: Int?,
    val results: List<Character>?,
    val total: Int?
)