package com.ucfjoe.marvelcomics.feature_marvel.presentation.ui.characters

import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Character

sealed class CharactersEvent {
    data class OnCharacterClicked(val character: Character) : CharactersEvent()
}
