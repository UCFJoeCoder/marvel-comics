package com.ucfjoe.marvelcomics.feature_marvel.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ucfjoe.marvelcomics.feature_marvel.data.local.entity.CharacterEntity

@Dao
interface MarvelDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCharacter(characterEntity: CharacterEntity)

    @Query("SELECT * FROM character_entity WHERE id=:characterId")
    suspend fun getCharacter(characterId: String): CharacterEntity?

}