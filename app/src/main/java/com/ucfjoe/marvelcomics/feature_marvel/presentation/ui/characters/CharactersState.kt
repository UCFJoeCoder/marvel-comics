package com.ucfjoe.marvelcomics.feature_marvel.presentation.ui.characters

import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Character

data class CharactersState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val page: Int = 0,
    val error: String? = null,
    val searchText: String = ""
)