package com.devkurt.markets.search.ui.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devkurt.markets.search.domain.api.repository.SearchRepository
import com.devkurt.markets.search.ui.impl.mapper.toUi
import com.devkurt.markets.ui.api.state.LoadingCounter
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

private const val QUERY_DEBOUNCE_MS = 400L

class SearchViewModel(
    private val searchRepository: SearchRepository,
) : ViewModel() {

    private val query = MutableStateFlow("")
    private val loading = LoadingCounter()

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = combine(
        _state,
        loading.isLoading,
    ) { currentState, isLoading ->
        currentState.copy(isLoading = isLoading)
    }.onStart {
        observeQuery()
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = _state.value,
    )

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.QueryChanged -> query.value = event.query
            SearchEvent.Retry -> search(query.value)
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeQuery() {
        query
            .debounce(QUERY_DEBOUNCE_MS.milliseconds)
            .distinctUntilChanged()
            .onEach { value -> search(value) }
            .launchIn(viewModelScope)
    }

    private fun search(rawQuery: String) {
        viewModelScope.launch {
            loading.withLoading {
                searchRepository.search(rawQuery.trim())
                    .onSuccess { coins ->
                        _state.update {
                            it.copy(
                                query = rawQuery,
                                results = coins.map { coin -> coin.toUi() },
                                error = null,
                            )
                        }
                    }
                    .onFailure { throwable ->
                        _state.update { it.copy(query = rawQuery, error = throwable.message) }
                    }
            }
        }
    }
}
