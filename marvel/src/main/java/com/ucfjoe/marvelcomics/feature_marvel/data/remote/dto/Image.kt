package com.ucfjoe.marvelcomics.feature_marvel.data.remote.dto

data class Image(
    val path: String,
    val extension: String
) {
    constructor(pathAndExtension: String):
            this(
                pathAndExtension.substringBeforeLast('.', ""),
                pathAndExtension.substringAfterLast('.', ""))

    override fun toString(): String = "$path.$extension"
}