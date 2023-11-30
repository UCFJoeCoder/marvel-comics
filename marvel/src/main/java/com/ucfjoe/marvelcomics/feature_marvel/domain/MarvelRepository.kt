package com.ucfjoe.marvelcomics.feature_marvel.domain

import com.ucfjoe.marvelcomics.core.util.Resource
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Character
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Comic
import kotlinx.coroutines.flow.Flow

interface MarvelRepository {

    fun getCharacters(offset: Int, limit: Int, name: String = ""): Flow<Resource<List<Character>>>

    fun getCharacter(characterId: String): Flow<Resource<Character>>

    suspend fun storeCharacter(character: Character)

    fun getComics(offset: Int, limit: Int, characterId: String): Flow<Resource<List<Comic>>>
}