package com.ucfjoe.marvelcomics.feature_marvel.presentation.ui.character

import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Character
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Comic

data class CharacterState(
    val character: Character =
        Character("0", "Unknown", "", "", 0, 0, 0, 0),
    val isLoading: Boolean = false,
    val error: String? = null,
    val comics: List<Comic> = emptyList(),
    val page: Int = 0,
    val endReached: Boolean = false,
    val showComicsOption: Boolean = false,
    val showComicsLink: Boolean = true,
    val showComics: Boolean = false
)
