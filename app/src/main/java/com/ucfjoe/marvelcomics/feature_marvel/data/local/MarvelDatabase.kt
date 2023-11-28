package com.ucfjoe.marvelcomics.feature_marvel.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ucfjoe.marvelcomics.feature_marvel.data.local.entity.CharacterEntity

@Database (
    entities = [CharacterEntity::class],
    version = 1
)
abstract class MarvelDatabase: RoomDatabase() {

    abstract val dao: MarvelDao
}