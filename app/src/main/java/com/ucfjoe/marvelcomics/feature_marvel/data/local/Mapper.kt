package com.ucfjoe.marvelcomics.feature_marvel.data.local

import com.ucfjoe.marvelcomics.feature_marvel.data.local.entity.CharacterEntity
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Character

fun CharacterEntity.toCharacter(): Character {
    return Character(
        characterId = this.characterId,
        name = this.name,
        description = this.description,
        thumbnail = this.thumbnail,
        comicsCounts = this.comicsCounts,
        storiesCount = this.storiesCount,
        eventsCount = this.eventsCount,
        seriesCount = this.seriesCount
    )
}

fun Character.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        characterId = this.characterId,
        name = this.name,
        description = this.description,
        thumbnail = this.thumbnail,
        comicsCounts = this.comicsCounts,
        storiesCount = this.storiesCount,
        eventsCount = this.eventsCount,
        seriesCount = this.seriesCount
    )
}
