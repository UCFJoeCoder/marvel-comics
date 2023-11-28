package com.ucfjoe.marvelcomics.feature_marvel.data.remote

import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Character
import com.ucfjoe.marvelcomics.feature_marvel.data.remote.dto.Character as CharacterNet
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Comic
import com.ucfjoe.marvelcomics.feature_marvel.data.remote.dto.Comic as ComicNet

fun CharacterNet.toCharacter(): Character {
    return Character(
        characterId = this.id,
        name = this.name ?: "[Unknown Name]",
        description = this.description ?: "",
        thumbnail = this.thumbnail.toString(),
        comicsCounts = this.comics?.available ?: 0,
        storiesCount = this.stories?.available ?: 0,
        eventsCount = this.events?.available ?: 0,
        seriesCount = this.series?.available ?: 0
    )
}

fun ComicNet.toComic(): Comic {
    return Comic(
        id = this.id ?: 0,
        title = this.title ?: "",
        issueNumber = this.issueNumber ?: 0.0,
        description = this.description ?: "",
        variantDescription = this.variantDescription ?: "",
        pageCount = this.pageCount ?: 0
    )
}
