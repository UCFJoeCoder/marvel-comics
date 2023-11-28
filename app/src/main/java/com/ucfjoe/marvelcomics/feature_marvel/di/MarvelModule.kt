package com.ucfjoe.marvelcomics.feature_marvel.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.ucfjoe.marvelcomics.feature_marvel.data.local.MarvelDatabase
import com.ucfjoe.marvelcomics.feature_marvel.data.remote.MarvelApi
import com.ucfjoe.marvelcomics.feature_marvel.data.remote.NetworkInterceptor
import com.ucfjoe.marvelcomics.feature_marvel.data.repository.MarvelRepositoryImpl
import com.ucfjoe.marvelcomics.feature_marvel.domain.MarvelRepository
import com.ucfjoe.marvelcomics.feature_marvel.domain.use_case.GetCharacter
import com.ucfjoe.marvelcomics.feature_marvel.domain.use_case.GetCharacters
import com.ucfjoe.marvelcomics.feature_marvel.domain.use_case.GetComics
import com.ucfjoe.marvelcomics.feature_marvel.domain.use_case.StoreCharacter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MarvelModule {

    @Provides
    @Singleton
    fun providesGetCharacters(marvelRepository: MarvelRepository): GetCharacters {
        return GetCharacters(marvelRepository)
    }

    @Provides
    @Singleton
    fun providesStoreCharacter(marvelRepository: MarvelRepository): StoreCharacter {
        return StoreCharacter(marvelRepository)
    }

    @Provides
    @Singleton
    fun providesGetCharacter(marvelRepository: MarvelRepository): GetCharacter {
        return GetCharacter(marvelRepository)
    }

    @Provides
    @Singleton
    fun providesGetComics(marvelRepository: MarvelRepository): GetComics {
        return GetComics(marvelRepository)
    }

    @Provides
    @Singleton
    fun provideMarvelRepository(api: MarvelApi, db: MarvelDatabase): MarvelRepository {
        Log.d("MarvelModule", "Resource : Create MarvelRepositoryImpl")
        return MarvelRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun providesMarvelDatabase(app: Application): MarvelDatabase {
        return Room.databaseBuilder(
            app, MarvelDatabase::class.java, "marvel_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMarvelApi(okHttpClient: OkHttpClient): MarvelApi {
        return Retrofit.Builder()
            .baseUrl(MarvelApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MarvelApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(NetworkInterceptor())
            .build()
    }
}