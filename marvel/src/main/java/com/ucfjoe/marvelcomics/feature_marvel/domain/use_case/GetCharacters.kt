package com.ucfjoe.marvelcomics.feature_marvel.domain.use_case

import com.ucfjoe.marvelcomics.core.util.Resource
import com.ucfjoe.marvelcomics.feature_marvel.domain.MarvelRepository
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCharacters(
    private val repository: MarvelRepository
) {

    operator fun invoke(
        limit: Int,
        offset: Int,
        name: String = ""
    ): Flow<Resource<List<Character>>> {
        if (limit < 0 || limit > 100 || offset < 0) {
            return flow { }
        }
        return repository.getCharacters(limit, offset, name)
    }

    operator fun invoke(
        limit: Int,
        offset: Int
    ): Flow<Resource<List<Character>>> {
        if (limit < 0 || limit > 100 || offset < 0) {
            return flow { }
        }
        return repository.getCharacters(limit, offset)
    }
}