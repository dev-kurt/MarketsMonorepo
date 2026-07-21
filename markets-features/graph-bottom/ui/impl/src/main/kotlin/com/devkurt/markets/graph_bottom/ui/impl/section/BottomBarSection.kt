package com.devkurt.markets.graph_bottom.ui.impl.section

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.devkurt.markets.graph_bottom.ui.api.GraphBottom
import com.devkurt.markets.graph_bottom.ui.api.LocalGraphBottom
import com.devkurt.markets.graph_bottom.ui.impl.R
import com.devkurt.markets.graph_dashboard.ui.api.GraphDashboardRoute
import com.devkurt.markets.graph_dashboard.ui.api.LocalGraphDashboard
import com.devkurt.markets.graph_list.ui.api.GraphListRoute
import com.devkurt.markets.graph_list.ui.api.LocalGraphList
import com.devkurt.markets.navigation.api.clearExceptRoot
import com.devkurt.markets.navigation.api.switchTo
import com.devkurt.markets.ui.api.bars.MkShortNavigationBarItem
import com.devkurt.markets.ui.api.display.MkIcon
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.testing.mkTestTag

private data class BottomBarItem(
    val icon: ImageVector,
    @StringRes val labelRes: Int,
    val route: GraphBottom,
    val testTag: String,
)

private val bottomBarItems = listOf(
    BottomBarItem(
        icon = Icons.Default.Home,
        labelRes = R.string.bottom_bar_dashboard,
        route = GraphDashboardRoute,
        testTag = "tab_dashboard",
    ),
    BottomBarItem(
        icon = Icons.AutoMirrored.Filled.List,
        labelRes = R.string.bottom_bar_coins,
        route = GraphListRoute,
        testTag = "tab_coins",
    ),
)

@Composable
fun BottomBarSection() {
    val bottomNavGraph = LocalGraphBottom.current
    val dashboardNavGraph = LocalGraphDashboard.current
    val listNavGraph = LocalGraphList.current

    val selectedBottomTab = bottomNavGraph.lastOrNull()
    val selectedDashboardTab = dashboardNavGraph.lastOrNull()
    val dashboardRoot = dashboardNavGraph.firstOrNull()
    val selectedListTab = listNavGraph.lastOrNull()
    val listRoot = listNavGraph.firstOrNull()

    bottomBarItems.forEach { tab ->
        val isSelected = selectedBottomTab == tab.route
        MkShortNavigationBarItem(
            modifier = Modifier.mkTestTag(tab.testTag),
            selected = isSelected,
            onClick = {
                when {
                    tab.route == GraphDashboardRoute &&
                            isSelected &&
                            selectedDashboardTab != dashboardRoot -> {
                        dashboardNavGraph.clearExceptRoot()
                    }

                    else -> {
                        bottomNavGraph.switchTo(tab.route)
                    }
                }
            },
            icon = { MkIcon(imageVector = tab.icon) },
            label = { MkText(text = stringResource(tab.labelRes), maxLines = 1) },
        )
    }
}
