package com.ucfjoe.marvelcomics.feature_marvel.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="character_entity")
data class CharacterEntity(
    @PrimaryKey @ColumnInfo(name="id") val characterId: String,
    @ColumnInfo(name="name") val name: String,
    @ColumnInfo(name="description")  val description: String,
    @ColumnInfo(name="image") val thumbnail: String,
    @ColumnInfo(name="total_comics") val comicsCounts: Int,
    @ColumnInfo(name="total_stories") val storiesCount: Int,
    @ColumnInfo(name="total_events") val eventsCount: Int,
    @ColumnInfo(name="total_series") val seriesCount: Int
)
