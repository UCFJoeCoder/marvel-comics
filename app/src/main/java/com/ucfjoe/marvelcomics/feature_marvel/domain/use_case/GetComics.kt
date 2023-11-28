package com.ucfjoe.marvelcomics.feature_marvel.domain.use_case

import com.ucfjoe.marvelcomics.core.util.Resource
import com.ucfjoe.marvelcomics.feature_marvel.domain.MarvelRepository
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Comic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetComics (
    private val repository: MarvelRepository
) {

    operator fun invoke(
        limit: Int,
        offset: Int,
        characterId: String = ""
    ): Flow<Resource<List<Comic>>> {
        if (limit < 0 || limit > 100 || offset < 0 || characterId.isBlank()) {
            return flow { }
        }
        return repository.getComics(limit, offset, characterId)
    }
}