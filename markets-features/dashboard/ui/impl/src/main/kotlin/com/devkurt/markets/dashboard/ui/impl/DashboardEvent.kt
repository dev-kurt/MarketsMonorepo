package com.devkurt.markets.dashboard.ui.impl

sealed interface DashboardEvent {
    data object Retry : DashboardEvent
}
