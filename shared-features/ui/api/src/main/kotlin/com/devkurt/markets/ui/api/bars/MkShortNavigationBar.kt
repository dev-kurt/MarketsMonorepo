package com.devkurt.markets.ui.api.bars

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationItemColors
import androidx.compose.material3.NavigationItemIconPosition
import androidx.compose.material3.ShortNavigationBar
import androidx.compose.material3.ShortNavigationBarArrangement
import androidx.compose.material3.ShortNavigationBarDefaults
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.ShortNavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MkShortNavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = ShortNavigationBarDefaults.containerColor,
    contentColor: Color = ShortNavigationBarDefaults.contentColor,
    windowInsets: WindowInsets = ShortNavigationBarDefaults.windowInsets,
    arrangement: ShortNavigationBarArrangement = ShortNavigationBarDefaults.arrangement,
    content: @Composable () -> Unit,
) {
    ShortNavigationBar(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        windowInsets = windowInsets,
        arrangement = arrangement,
        content = content,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MkShortNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    label: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    iconPosition: NavigationItemIconPosition = NavigationItemIconPosition.Top,
    colors: NavigationItemColors = ShortNavigationBarItemDefaults.colors(),
) {
    ShortNavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = icon,
        label = label,
        modifier = modifier,
        enabled = enabled,
        iconPosition = iconPosition,
        colors = colors,
    )
}
