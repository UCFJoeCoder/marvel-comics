package com.ucfjoe.marvelcomics.feature_marvel.data.repository

import android.util.Log
import com.ucfjoe.marvelcomics.core.util.Resource
import com.ucfjoe.marvelcomics.feature_marvel.data.local.MarvelDao
import com.ucfjoe.marvelcomics.feature_marvel.data.local.toCharacter
import com.ucfjoe.marvelcomics.feature_marvel.data.local.toCharacterEntity
import com.ucfjoe.marvelcomics.feature_marvel.data.remote.MarvelApi
import com.ucfjoe.marvelcomics.feature_marvel.data.remote.toCharacter
import com.ucfjoe.marvelcomics.feature_marvel.data.remote.toComic
import com.ucfjoe.marvelcomics.feature_marvel.domain.MarvelRepository
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Character
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Comic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MarvelRepositoryImpl(
    private val api: MarvelApi,
    private val dao: MarvelDao
) : MarvelRepository {

    override fun getCharacters(
        limit: Int,
        offset: Int,
        name: String
    ): Flow<Resource<List<Character>>> = flow {
        emit(Resource.Loading())

        val characterDataWrapper = try {
            if (name.isBlank()) {
                api.getCharacters(limit, offset)
            } else {
                api.getCharactersByNameStartsWith(limit, offset, name)
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Failed to get Characters from Web"))
            return@flow
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server, check your internet connection"))
            return@flow
        }

        Log.d("MarvelRepositoryImpl", "Reso ${characterDataWrapper.body()}")

        val charactersNet = characterDataWrapper.body()?.data?.results ?: emptyList()
        val characters = charactersNet.map { it.toCharacter() }

        emit(Resource.Success(characters))
    }

    override fun getCharacter(characterId: String): Flow<Resource<Character>> = flow {
        emit(Resource.Loading())

        var character: Character?

        character = dao.getCharacter(characterId)?.toCharacter()
        if (character == null) {

            val characterDataWrapper = try {
                api.getCharacter(characterId)
            } catch (e: HttpException) {
                emit(Resource.Error("Failed to get Characters from Web"))
                return@flow
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server, check your internet connection"))
                return@flow
            }

            val charactersNet = characterDataWrapper.body()?.data?.results ?: emptyList()
            if (charactersNet.isEmpty()) {
                emit(Resource.Error("Character with id '$characterId' not found"))
                return@flow
            }
            character = charactersNet.first().toCharacter()
            dao.insertCharacter(character.toCharacterEntity())
        }

        emit(Resource.Success(character))
    }

    override suspend fun storeCharacter(character: Character) {
        dao.insertCharacter(character.toCharacterEntity())
    }

    override fun getComics(
        limit: Int,
        offset: Int,
        characterId: String
    ): Flow<Resource<List<Comic>>> = flow {
        emit(Resource.Loading())

        val comicDataWrapper = try {
            if (characterId.isBlank()) {
                emit(Resource.Error("CharacterId is required for retrieving comics"))
                return@flow
            }
            else {
                api.getComicsByCharacterId(limit, offset, characterId)
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Failed to get Comics from Web"))
            return@flow
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server, check your internet connection"))
            return@flow
        }

        val comicsNet = comicDataWrapper.body()?.data?.results ?: emptyList()
        val comics = comicsNet.map { it.toComic() }

        emit(Resource.Success(comics))
    }
}