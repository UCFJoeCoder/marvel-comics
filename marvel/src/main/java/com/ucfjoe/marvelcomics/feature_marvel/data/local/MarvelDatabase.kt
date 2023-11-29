package com.ucfjoe.marvelcomics.feature_marvel.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ucfjoe.marvelcomics.feature_marvel.data.local.entity.CharacterEntity

@Database(
    version = 1,
    entities = [CharacterEntity::class],
    exportSchema = true
)
abstract class MarvelDatabase : RoomDatabase() {

    abstract val dao: MarvelDao

    // sqlite doesn't have the concept of ALTER COLUMN... so, because I don't want to jump through
    // hoops. I am just clearing the app data on the only phone this is installed on and sticking
    // with database version 1
//    companion object {
//        val migrationFrom1To2 = object : Migration(1, 2) {
//            override fun migrate(db: SupportSQLiteDatabase) {
//                db.execSQL("ALTER TABLE character_entity ALTER COLUMN 'description' TEXT NULL")
//                db.execSQL("UPDATE character_entity SET description=NULL WHERE description=''")
//            }
//        }
//    }
}