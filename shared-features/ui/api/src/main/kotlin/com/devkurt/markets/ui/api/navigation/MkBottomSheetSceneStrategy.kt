package com.devkurt.markets.ui.api.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.OverlayScene
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.SceneStrategyScope
import com.devkurt.markets.ui.api.feedback.MkModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
class MkBottomSheetSceneStrategy<T : Any>(
    private val backStack: MutableList<T>,
) : SceneStrategy<T> {

    override fun SceneStrategyScope<T>.calculateScene(entries: List<NavEntry<T>>): Scene<T>? {
        val lastEntry = entries.lastOrNull() ?: return null
        val properties = lastEntry.metadata[BOTTOM_SHEET_KEY] as? ModalBottomSheetProperties
            ?: return null
        val skipPartiallyExpanded =
            lastEntry.metadata[SKIP_PARTIALLY_EXPANDED_KEY] as? Boolean == true

        val backstack = entries.dropLast(1)
        if (backstack.isEmpty()) return null

        val sheetKey = backStack.lastOrNull() ?: return null
        return BottomSheetOverlayScene(
            entry = lastEntry,
            backstack = backstack,
            properties = properties,
            skipPartiallyExpanded = skipPartiallyExpanded,
            onDismiss = { backStack.remove(sheetKey) },
        )
    }

    companion object {
        const val BOTTOM_SHEET_KEY = "bottomsheet"
        const val SKIP_PARTIALLY_EXPANDED_KEY = "bottomsheet_skip_partially_expanded"

        @OptIn(ExperimentalMaterial3Api::class)
        fun bottomSheet(skipPartiallyExpanded: Boolean = false): Map<String, Any> = mapOf(
            BOTTOM_SHEET_KEY to ModalBottomSheetProperties(),
            SKIP_PARTIALLY_EXPANDED_KEY to skipPartiallyExpanded,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private class BottomSheetOverlayScene<T : Any>(
    private val entry: NavEntry<T>,
    private val backstack: List<NavEntry<T>>,
    private val properties: ModalBottomSheetProperties,
    private val skipPartiallyExpanded: Boolean,
    private val onDismiss: () -> Unit,
) : OverlayScene<T> {

    override val key: Any = entry.contentKey
    override val entries: List<NavEntry<T>> = listOf(entry)
    override val previousEntries: List<NavEntry<T>> = backstack
    override val overlaidEntries: List<NavEntry<T>> = backstack

    override val content: @Composable () -> Unit = {
        MkModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded),
            properties = properties,
        ) {
            entry.Content()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BottomSheetOverlayScene<*>) return false
        return key == other.key
    }

    override fun hashCode(): Int = key.hashCode()
}
