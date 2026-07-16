package com.devkurt.markets.template_feature.ui.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devkurt.markets.template_feature.domain.api.usecase.TemplateFeatureUseCase
import com.devkurt.markets.ui.api.state.LoadingCounter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TemplateFeatureViewModel(
    private val templateFeatureUseCase: TemplateFeatureUseCase,
) : ViewModel() {

    private val loading = LoadingCounter()

    private val _state = MutableStateFlow(TemplateFeatureState())
    val state: StateFlow<TemplateFeatureState> = combine(
        _state,
        loading.isLoading,
    ) { currentState, isLoading ->
        currentState.copy(
            isLoading = isLoading,
        )
    }.onStart {
        load()
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = _state.value,
    )

    fun onEvent(event: TemplateFeatureEvent) {
        when (event) {
            is TemplateFeatureEvent.UpdateState -> {
                _state.update { state -> event.update(state) }
            }

            TemplateFeatureEvent.Refresh -> load()
        }
    }

    private fun load() {
        viewModelScope.launch {
            loading.withLoading {
                templateFeatureUseCase()
                    .onSuccess { feature ->
                        _state.update { it.copy(name = feature.name, error = null) }
                    }
                    .onFailure { throwable ->
                        _state.update { it.copy(error = throwable.message) }
                    }
            }
        }
    }
}
