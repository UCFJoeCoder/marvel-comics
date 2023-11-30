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

class GetCharactersTest {
    companion object {
        const val PAGE_SIZE = 20
    }

    @Test
    fun `Pass invalid page size, too big`() = runBlocking {
        val invalidPageSize = 101
        val fakeMarvelRepo = FakeMarvelRepository(Utils.createTestCharacters(2))
        val getCharacters = GetCharacters(fakeMarvelRepo)

        when (val results = getCharacters(Page(invalidPageSize)).first()) {
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
        val fakeMarvelRepo = FakeMarvelRepository(Utils.createTestCharacters(2))
        val getCharacters = GetCharacters(fakeMarvelRepo)

        when (val results = getCharacters(Page(invalidPageSize)).first()) {
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
        val fakeMarvelRepo = FakeMarvelRepository(Utils.createTestCharacters(2))
        val getCharacters = GetCharacters(fakeMarvelRepo)

        when (val results = getCharacters(Page(PAGE_SIZE, invalidPageIndex)).first()) {
            is Resource.Error -> {
                Truth.assertThat(results.message).contains("negative")
            }

            else -> {
                Assert.fail("Did not expect Success or Loading to be emitted")
            }
        }
    }

    @Test
    fun `Get two pages of characters, success`() = runBlocking {

        val page = Page(PAGE_SIZE)
        val additionalCharacters = 5
        val numberOfCharactersInRepo = PAGE_SIZE + additionalCharacters
        val fakeMarvelRepo =
            FakeMarvelRepository(Utils.createTestCharacters(numberOfCharactersInRepo))
        val getCharacters = GetCharacters(fakeMarvelRepo)

        // Retrieve the first page and verify there are PAGE_SIZE items in results.
        when (val results = getCharacters(page).first()) {
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
        when (val results = getCharacters(page).first()) {
            is Resource.Success -> {
                Truth.assertThat(results.data?.size).isEqualTo(additionalCharacters)
            }

            else -> {
                Assert.fail("Error or Loading should not be emitted")
            }
        }
    }

}