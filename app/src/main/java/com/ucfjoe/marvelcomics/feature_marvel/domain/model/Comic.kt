package com.ucfjoe.marvelcomics.feature_marvel.domain.model

data class Comic(
    val id:Int,
    val title:String,
    val issueNumber:Double,
    val description:String,
    val variantDescription:String,
    val pageCount:Int
)
