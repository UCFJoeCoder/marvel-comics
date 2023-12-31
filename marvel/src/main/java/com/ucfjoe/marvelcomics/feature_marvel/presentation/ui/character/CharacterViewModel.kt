package com.ucfjoe.marvelcomics.feature_marvel.presentation.ui.character

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucfjoe.marvelcomics.core.util.Resource
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Page
import com.ucfjoe.marvelcomics.feature_marvel.domain.use_case.GetCharacter
import com.ucfjoe.marvelcomics.feature_marvel.domain.use_case.GetComics
import com.ucfjoe.marvelcomics.feature_marvel.presentation.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharacter: GetCharacter,
    private val getComics: GetComics,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(CharacterState(page = Page(PAGE_SIZE)))
    val state: State<CharacterState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val characterId: String? = savedStateHandle["character_id"]
        characterId?.let {
            loadCharacter(it)
        }
    }

    private fun loadCharacter(characterId: String) {
        viewModelScope.launch() {
            getCharacter(characterId)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            val character = result.data!!
                            _state.value = state.value.copy(
                                character = character,
                                isLoading = false
                            )
                            _state.value =
                                state.value.copy(showComicsOption = (character.comicsCounts > 0))
                        }

                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                isLoading = false,
                                error = result.message ?: "Unknown error"
                            )
                        }

                        is Resource.Loading -> {
                            _state.value = state.value.copy(isLoading = true)
                        }
                    }
                }.launchIn(this)
        }
    }

    fun onShowComicsClicked() {
        _state.value = state.value.copy(showComicsLink = false, showComics = true)
        loadComicsPaginated()
    }

    private fun loadComicsPaginated() {
        viewModelScope.launch {
            getComics(state.value.page, state.value.character.characterId)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                comics = state.value.comics + (result.data ?: emptyList()),
                                endReached = PAGE_SIZE > result.data!!.count(),
                                isLoading = false
                            )
                            state.value.page.nextPage()
                        }

                        is Resource.Error -> {
                            _state.value = state.value.copy(isLoading = false)
                            sendUiEvent(
                                UiEvent.ShowSnackbar(
                                    result.message ?: "Unknown error getting comics"
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

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    /**
     * If the index of the item being requested is greater than or equal to the max item in the list
     * then try to load more date... unless we know we are at the end of the list or there is already
     * a request pending for loading more data
     *
     * @param itemIndex index of the item being requested
     */
    fun checkIfMorePagesAreNeeded(itemIndex: Int) {
        if (itemIndex >= state.value.comics.size - 1 && !state.value.endReached && !state.value.isLoading) {
            loadComicsPaginated()
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}