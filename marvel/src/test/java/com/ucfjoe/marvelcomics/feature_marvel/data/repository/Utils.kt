package com.ucfjoe.marvelcomics.feature_marvel.data.repository

import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Character
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Comic
import java.time.LocalDate
import kotlin.random.Random

object Utils {


    /**
     * Returns a list of characters. Range for numberOfCharacters is from 1 to 100
     *
     * @param numberOfCharacters
     * @return
     */
    fun createTestCharacters(numberOfCharacters: Int): List<Character> {
        if (numberOfCharacters < 1) return emptyList()
        val newNumOfCharacters = if (numberOfCharacters > 100) 100 else numberOfCharacters
        return MutableList(newNumOfCharacters) { createTestCharacter(getCharacterName(it)) }
    }

    fun createTestComics(numberOfComics: Int, characterName:String): List<Comic> {
        if (numberOfComics < 1) return emptyList()
        val newNumberOfComics = if (numberOfComics > 100) 100 else numberOfComics
        return MutableList(newNumberOfComics) { createTestComic(characterName) }
    }

    private fun createTestCharacter(name: String) = Character(
        Random.nextInt(10000, 90000).toString(),
        name,
        "Character Description",
        "character.png",
        Random.nextInt(0, 6),
        Random.nextInt(0, 50),
        Random.nextInt(0, 50),
        Random.nextInt(0, 50)
    )

    private fun createTestComic(characterName: String) = Comic(
        Random.nextInt(10000, 90000),
        "$characterName comic ${Random.nextInt(0, 50)}",
        Random.nextDouble(1.0, 50.0),
        "Comic Description",
        "",
        Random.nextInt(10, 100),
        LocalDate.parse("2023-11-18"),
        "comic.png",
        listOf(characterName).toList()
    )

    /**
     * returns a character name based on an index of that name in the list.
     * If the index is outside the bounds of the list then a number is appended to the end.d
     * For example index 0 is "Spider-Man", If there are 10 names in the list then index 10 would
     * be out of bounds. So this method starts the list over again with the number 2 appended to
     * the end. A request for index 10, in the above example, would return "Spider-Man2". A
     * request for index 20 would return "Spider-Man3". This ensures all names are un
     *
     * @param index of the character name to retrieve
     * @return
     */
    private fun getCharacterName(index: Int): String {
        return if (index < characterNames.size) {
            characterNames[index]
        } else {
            val number = index / characterNames.size
            val newIndex = index % characterNames.size
            "${characterNames[newIndex]}$number"
        }
    }


    private val characterNames = listOf(
        "Spider-Man",
        "Thor",
        "Iron Man",
        "Deadpool",
        "Hulk",
        "Hawkeye",
        "Star-Lord",
        "Groot",
        "Black Widow",
        "Captain America",
        "Captain Marvel",
        "Doctor Strange",
        "Wolverine",
        "Black Panther",
        "Scarlet Witch",
        "Ghost Rider",
        "Ant Man",
        "Rocket Raccoon",
        "Silver Surfer",
        "Daredevil",
        "Vision"
    )

}