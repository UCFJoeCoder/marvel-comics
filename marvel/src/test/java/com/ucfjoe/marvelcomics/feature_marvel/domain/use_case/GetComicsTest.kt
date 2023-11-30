package com.ucfjoe.marvelcomics.feature_marvel.domain.use_case

import com.google.common.truth.Truth
import com.ucfjoe.marvelcomics.core.util.Resource
import com.ucfjoe.marvelcomics.feature_marvel.data.repository.FakeMarvelRepository
import com.ucfjoe.marvelcomics.feature_marvel.data.repository.Utils
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Page
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetComicsTest {

    companion object {
        const val PAGE_SIZE = 20
    }

    @Test
    fun `Pass invalid page size, too big`() = runBlocking {
        val invalidPageSize = 101
        val testCharacters = Utils.createTestCharacters(1)
        val testCharacterId = testCharacters[0].characterId
        val fakeMarvelRepo =
            FakeMarvelRepository(testCharacters, Utils.createTestComics(1, testCharacters[0].name))
        val getComics = GetComics(fakeMarvelRepo)

        when (val results = getComics(Page(invalidPageSize), testCharacterId).first()) {
            is Resource.Error -> {
                Truth.assertThat(results.message).contains("Page size")
            }

            else -> {
                Assert.fail("Did not expect Success or Loading to be emitted")
            }
        }
    }

    @Test
    fun `Pass invalid page size, too small`() = runBlocking {
        val invalidPageSize = 0
        val testCharacters = Utils.createTestCharacters(1)
        val testCharacterId = testCharacters[0].characterId
        val fakeMarvelRepo =
            FakeMarvelRepository(testCharacters, Utils.createTestComics(1, testCharacters[0].name))
        val getComics = GetComics(fakeMarvelRepo)

        when (val results = getComics(Page(invalidPageSize), testCharacterId).first()) {
            is Resource.Error -> {
                Truth.assertThat(results.message).contains("Page size")
            }

            else -> {
                Assert.fail("Did not expect Success or Loading to be emitted")
            }
        }
    }

    @Test
    fun `Pass invalid page index, negative number`() = runBlocking {
        val invalidPageIndex = -1
        val testCharacters = Utils.createTestCharacters(1)
        val testCharacterId = testCharacters[0].characterId
        val fakeMarvelRepo =
            FakeMarvelRepository(testCharacters, Utils.createTestComics(1, testCharacters[0].name))
        val getComics = GetComics(fakeMarvelRepo)

        when (val results = getComics(
            Page(GetCharactersTest.PAGE_SIZE, invalidPageIndex),
            testCharacterId
        ).first()) {
            is Resource.Error -> {
                Truth.assertThat(results.message).contains("page index")
            }

            else -> {
                Assert.fail("Did not expect Success or Loading to be emitted")
            }
        }
    }

    @Test
    fun `Pass invalid character Id`() = runBlocking {
        val testCharacters = Utils.createTestCharacters(1)
        val invalidCharacterId = ""
        val fakeMarvelRepo =
            FakeMarvelRepository(testCharacters, Utils.createTestComics(1, testCharacters[0].name))
        val getComics = GetComics(fakeMarvelRepo)

        when (val results = getComics(Page(GetCharactersTest.PAGE_SIZE), invalidCharacterId).first()) {
            is Resource.Error -> {
                Truth.assertThat(results.message).contains("characterId")
            }

            else -> {
                Assert.fail("Did not expect Success or Loading to be emitted")
            }
        }
    }

    @Test
    fun `Get two pages of comics, success`() = runBlocking {

        val page = Page(PAGE_SIZE)
        val numberOfCharactersInRepo = 1
        val additionalComics = 3
        val numberOfComicsInRepo = PAGE_SIZE + additionalComics
        val characters = Utils.createTestCharacters(numberOfCharactersInRepo)
        val testCharacterId = characters[0].characterId
        val fakeMarvelRepo =
            FakeMarvelRepository(
                characters,
                Utils.createTestComics(numberOfComicsInRepo, characters[0].name)
            )
        val getComics = GetComics(fakeMarvelRepo)

        // Retrieve the first page and verify there are PAGE_SIZE items in results.
        when (val results = getComics(page, testCharacterId).first()) {
            is Resource.Success -> {
                // NOTE: Testing equal to PAGE_SIZE assumes the original collection has 20
                // or more characters.
                Truth.assertThat(results.data?.size).isEqualTo(PAGE_SIZE)
            }

            else -> {
                Assert.fail("Error or Loading should not be emitted")
            }
        }

        page.nextPage()

        // Retrieve the second page and verify there are additionalCharacters items in results
        when (val results = getComics(page, testCharacterId).first()) {
            is Resource.Success -> {
                Truth.assertThat(results.data?.size).isEqualTo(additionalComics)
            }

            else -> {
                Assert.fail("Error or Loading should not be emitted")
            }
        }
    }
}