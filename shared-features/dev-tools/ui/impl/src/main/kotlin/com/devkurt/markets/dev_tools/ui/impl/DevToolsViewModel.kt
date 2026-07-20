package com.devkurt.markets.dev_tools.ui.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devkurt.markets.dev_tools.ui.api.DevToolsAction
import com.devkurt.markets.ui.api.state.LoadingCounter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DevToolsViewModel(
    private val actions: List<DevToolsAction>,
) : ViewModel() {

    private val loading = LoadingCounter()

    private val _state = MutableStateFlow(
        DevToolsState(actionTitleResIds = actions.map { action -> action.titleRes }),
    )
    val state: StateFlow<DevToolsState> = combine(
        _state,
        loading.isLoading,
    ) { currentState, isLoading ->
        currentState.copy(isLoading = isLoading)
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = _state.value,
    )

    private val _effect = Channel<DevToolsEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: DevToolsEvent) {
        when (event) {
            is DevToolsEvent.ExecuteAction -> execute(event.index)
        }
    }

    private fun execute(index: Int) {
        val action = actions.getOrNull(index) ?: return
        viewModelScope.launch {
            loading.withLoading {
                action.execute()
                    .onSuccess { _effect.send(DevToolsEffect.ShowMessage(action.successMessageRes)) }
                    .onFailure { _effect.send(DevToolsEffect.ShowMessage(R.string.dev_tools_action_failed)) }
            }
        }
    }
}
