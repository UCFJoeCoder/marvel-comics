package com.ucfjoe.marvelcomics.feature_marvel.presentation.ui.characters

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucfjoe.marvelcomics.Screen
import com.ucfjoe.marvelcomics.core.util.Resource
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Page
import com.ucfjoe.marvelcomics.feature_marvel.domain.use_case.GetCharacters
import com.ucfjoe.marvelcomics.feature_marvel.domain.use_case.StoreCharacter
import com.ucfjoe.marvelcomics.feature_marvel.presentation.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharacters: GetCharacters,
    private val storeCharacter: StoreCharacter
) : ViewModel() {

    private val _state = mutableStateOf(CharactersState(page = Page(PAGE_SIZE)))
    val state: State<CharactersState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: CharactersEvent) {
        when (event) {
            is CharactersEvent.OnCharacterClicked -> {
                viewModelScope.launch { storeCharacter(event.character) }
                sendUiEvent(UiEvent.Navigation(Screen.CharacterScreen.route + "?character_id=${event.character.characterId}"))
            }
        }
    }

    init {
        loadCharactersPaginated()
    }

    private fun loadCharactersPaginated() {
        viewModelScope.launch {
            getCharacters(state.value.page, state.value.searchText)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                characters = state.value.characters + (result.data ?: emptyList()),
                                endReached = PAGE_SIZE > result.data!!.count(),
                                isLoading = false
                            )
                            state.value.page.nextPage()
                        }

                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                //characters = result.data ?: emptyList(),
                                //endReached = state.value.page * PAGE_SIZE >= result.data!!.count(),
                                isLoading = false
                            )
                            sendUiEvent(
                                UiEvent.ShowSnackbar(
                                    result.message ?: "Unknown error getting characters"
                                )
                            )
                        }

                        is Resource.Loading -> {
                            _state.value = state.value.copy(isLoading = true)
                        }
                    }
                }.launchIn(this)
        }
    }

    fun checkIfMorePagesAreNeeded(itemIndex: Int) {
        if (itemIndex >= state.value.characters.size - 1 && !state.value.endReached && !state.value.isLoading) {
            loadCharactersPaginated()
        }
    }

    fun onSearch(searchText: String) {
        // Clear previous search Results by resetting everything back to default values
        // But set the search String
        _state.value = state.value.copy(
            characters = emptyList(),
            isLoading = false,
            endReached = false,
            error = null,
            searchText = searchText.trim()
        )
        state.value.page.goToFirstPage()
        loadCharactersPaginated()
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}