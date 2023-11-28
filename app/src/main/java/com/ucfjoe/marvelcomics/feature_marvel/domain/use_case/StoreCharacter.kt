package com.ucfjoe.marvelcomics.feature_marvel.domain.use_case

import com.ucfjoe.marvelcomics.feature_marvel.domain.MarvelRepository
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Character

class StoreCharacter(
    private val repository: MarvelRepository
) {
    suspend operator fun invoke(
        character: Character
    ) {
        repository.storeCharacter(character)
    }
}