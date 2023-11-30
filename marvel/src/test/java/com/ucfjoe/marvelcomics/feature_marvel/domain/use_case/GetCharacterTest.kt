package com.ucfjoe.marvelcomics.feature_marvel.domain.use_case

import com.google.common.truth.Truth
import com.ucfjoe.marvelcomics.core.util.Resource
import com.ucfjoe.marvelcomics.feature_marvel.data.repository.FakeMarvelRepository
import kotlinx.coroutines.flow.first
import com.ucfjoe.marvelcomics.feature_marvel.data.repository.Utils
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetCharacterTest {

    @Test
    fun `GetCharacter, missing characterId, error`() = runBlocking {

        val invalidCharacterId = ""
        val getCharacter = GetCharacter(FakeMarvelRepository(emptyList()))

        when (val results = getCharacter(invalidCharacterId).first()) {
            is Resource.Error -> {
                Truth.assertThat(results.message).contains("CharacterId")
            }

            else -> {
                Assert.fail("Did not expect for success or loading to be emitted.")
            }
        }
    }

    @Test
    fun `GetCharacter, success`() = runBlocking {
        // Create list of three characters
        val characters = Utils.createTestCharacters(3)
        val getCharacter = GetCharacter(FakeMarvelRepository(characters))
        // Get the second character from the list
        val expectedCharacter = characters[1]

        when (val results = getCharacter(expectedCharacter.characterId).first()) {
            is Resource.Success -> {
                Truth.assertThat(results.data).isEqualTo(expectedCharacter)
            }
            else -> {
                Assert.fail("Loading and Error are not expected results")
            }
        }

    }

}