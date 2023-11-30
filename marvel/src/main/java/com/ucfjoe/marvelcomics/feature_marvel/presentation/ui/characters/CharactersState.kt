package com.ucfjoe.marvelcomics.feature_marvel.presentation.ui.characters

import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Character
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Page

data class CharactersState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val page: Page,
    val error: String? = null,
    val searchText: String = ""
)