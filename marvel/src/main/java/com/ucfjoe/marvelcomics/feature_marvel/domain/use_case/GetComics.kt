package com.ucfjoe.marvelcomics.feature_marvel.domain.use_case

import com.ucfjoe.marvelcomics.core.util.Resource
import com.ucfjoe.marvelcomics.feature_marvel.domain.MarvelRepository
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Comic
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Page
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetComics(
    private val repository: MarvelRepository
) {

    operator fun invoke(
        page: Page,
        characterId: String
    ): Flow<Resource<List<Comic>>> {
        if (page.size < 1 || page.size > 100 || page.index < 0 || characterId.isBlank()) {
            return flow {
                emit(Resource.Error("Page size is outside of bounds or page index is negative or characterId was not provided"))
            }
        }
        return repository.getComics(page.getIndexOffset(), page.size, characterId)
    }

}