package com.ucfjoe.marvelcomics.feature_marvel.data.remote

import com.ucfjoe.marvelcomics.feature_marvel.data.remote.dto.CharacterDataWrapper
import com.ucfjoe.marvelcomics.feature_marvel.data.remote.dto.ComicDataWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<CharacterDataWrapper>

    @GET("/v1/public/characters")
    suspend fun getCharactersByNameStartsWith(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("nameStartsWith") name: String
    ): Response<CharacterDataWrapper>

    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharacter(
        @Path("characterId") id: String
    ): Response<CharacterDataWrapper>

    // API accepts a comma separated list of characterIds. For this project, only a single characterId is allowed
    @GET("v1/public/comics")
    suspend fun getComicsByCharacterId(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("characters") id: String
    ): Response<ComicDataWrapper>

    companion object {
        const val BASE_URL = "https://gateway.marvel.com/"
    }


}