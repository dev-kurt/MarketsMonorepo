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

        val backstack = entries.dropLast(1)
        if (backstack.isEmpty()) return null

        val sheetKey = backStack.lastOrNull() ?: return null
        return BottomSheetOverlayScene(
            entry = lastEntry,
            backstack = backstack,
            properties = properties,
            onDismiss = { backStack.remove(sheetKey) },
        )
    }

    companion object {
        const val BOTTOM_SHEET_KEY = "bottomsheet"

        @OptIn(ExperimentalMaterial3Api::class)
        fun bottomSheet(): Map<String, Any> =
            mapOf(BOTTOM_SHEET_KEY to ModalBottomSheetProperties())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private class BottomSheetOverlayScene<T : Any>(
    private val entry: NavEntry<T>,
    private val backstack: List<NavEntry<T>>,
    private val properties: ModalBottomSheetProperties,
    private val onDismiss: () -> Unit,
) : OverlayScene<T> {

    override val key: Any = entry.contentKey
    override val entries: List<NavEntry<T>> = listOf(entry)
    override val previousEntries: List<NavEntry<T>> = backstack
    override val overlaidEntries: List<NavEntry<T>> = backstack

    override val content: @Composable () -> Unit = {
        MkModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
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
