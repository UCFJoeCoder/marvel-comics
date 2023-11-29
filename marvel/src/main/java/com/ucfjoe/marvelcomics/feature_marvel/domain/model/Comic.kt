package com.ucfjoe.marvelcomics.feature_marvel.domain.model

import java.time.LocalDate

data class Comic(
    val id:Int,
    val title:String,
    val issueNumber:Double,
    val description:String?,
    val variantDescription:String,
    val pageCount:Int?,
    val onSaleDate: LocalDate?,
    val thumbnail: String?,
    val characters: List<String>?
)
