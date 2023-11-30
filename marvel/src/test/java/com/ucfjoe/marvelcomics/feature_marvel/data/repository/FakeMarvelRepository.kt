package com.ucfjoe.marvelcomics.feature_marvel.data.repository

import com.ucfjoe.marvelcomics.core.util.Resource
import com.ucfjoe.marvelcomics.feature_marvel.domain.MarvelRepository
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Character
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Comic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMarvelRepository(
    private val characters: List<Character>,
    private val comics: List<Comic> = emptyList()
) : MarvelRepository {

    override fun getCharacters(
        offset: Int,
        limit: Int,
        name: String
    ): Flow<Resource<List<Character>>> {

        val returnCharacters = if (name.isBlank()) {
            characters
        } else {
            characters.filter { character -> character.name.startsWith(name, true) }
        }

        // If offset is greater than size then there is nothing to return
        if (offset >= returnCharacters.size) {
            // Start index it out of bounds
            return flow {}
        }
        return flow {
            var end = offset + limit
            // Check if the end index would be out of bounds
            if (end > returnCharacters.size) {
                end = returnCharacters.size
            }
            emit(Resource.Success(returnCharacters.slice(offset until end)))
        }
    }

    override fun getCharacter(characterId: String): Flow<Resource<Character>> {
        return flow { Resource.Success(characters.find { it.characterId == characterId }) }
    }

    override suspend fun storeCharacter(character: Character) {
        // Not sure if implementing this for testing adds value.

        // The purpose of storing the character to the database is to look it up when
        // navigating between screen. but the logic is written to pull it from the internet
        // if it cannot find it. Since we are faking pulling from the database... then faking
        // storing it just to fake pull it seems silly. Too much fake going on to add value.
    }

    override fun getComics(
        offset: Int,
        limit: Int,
        characterId: String
    ): Flow<Resource<List<Comic>>> {
        if (characterId.isBlank()) {
            return flow {}
        }

        val characterName =
            characters.find { it.characterId == characterId }?.name ?: return flow {}

        val returnComics = comics.filter { it.characters!!.contains(characterName) }

        // If offset is greater than size then there is nothing to return
        if (offset >= returnComics.size) {
            // Start index it out of bounds
            return flow {}
        }
        return flow {
            var end = offset + limit
            // Check if the end index would be out of bounds
            if (end > returnComics.size) {
                end = returnComics.size
            }
            emit(Resource.Success(returnComics.slice(offset until end)))
        }
    }
}