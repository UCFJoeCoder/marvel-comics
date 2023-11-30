package com.ucfjoe.marvelcomics.feature_marvel.domain.model

data class Page(
    val size:Int,
    var index:Int = 0
) {
    fun getIndexOffset() = index * size
    fun nextPage() { index++ }

    fun goToFirstPage() { index = 0 }
}
