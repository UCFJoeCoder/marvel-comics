package com.ucfjoe.marvelcomics.feature_marvel.domain.use_case

import com.ucfjoe.marvelcomics.core.util.Resource
import com.ucfjoe.marvelcomics.feature_marvel.domain.MarvelRepository
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Character
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Page
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCharacters(
    private val repository: MarvelRepository
) {

    operator fun invoke(
        page: Page,
        name: String = ""
    ): Flow<Resource<List<Character>>> {
        if (page.size < 1 || page.size > 100 || page.index < 0) {
            return flow {
                emit(Resource.Error("Page size is outside of bounds or page index is negative"))
            }
        }
        return repository.getCharacters(page.getIndexOffset(), page.size, name)
    }

}