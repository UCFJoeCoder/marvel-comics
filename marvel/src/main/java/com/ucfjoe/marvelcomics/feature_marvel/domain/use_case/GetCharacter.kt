package com.ucfjoe.marvelcomics.feature_marvel.domain.use_case

import com.ucfjoe.marvelcomics.core.util.Resource
import com.ucfjoe.marvelcomics.feature_marvel.domain.MarvelRepository
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCharacter(
    private val repository: MarvelRepository
) {
    operator fun invoke(characterId: String): Flow<Resource<Character>> {
        if (characterId.isBlank()) {
            return flow { emit(Resource.Error("CharacterId is required.")) }
        }
        return repository.getCharacter(characterId)
    }
}