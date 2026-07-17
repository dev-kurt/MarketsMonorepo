package com.devkurt.markets.graph_bottom.ui.impl.section

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.devkurt.markets.graph_bottom.ui.api.GraphBottom
import com.devkurt.markets.graph_bottom.ui.api.LocalGraphBottom
import com.devkurt.markets.graph_dashboard.ui.api.GraphDashboardRoute
import com.devkurt.markets.graph_dashboard.ui.api.LocalGraphDashboard
import com.devkurt.markets.navigation.api.clearExceptRoot
import com.devkurt.markets.navigation.api.switchTo
import com.devkurt.markets.ui.api.bars.MkShortNavigationBarItem
import com.devkurt.markets.ui.api.display.MkIcon
import com.devkurt.markets.ui.api.display.MkText

private data class BottomBarItem(
    val icon: ImageVector,
    val label: String,
    val route: GraphBottom,
)

private val bottomBarItems = listOf(
    BottomBarItem(
        icon = Icons.Default.Home,
        label = "Piyasa",
        route = GraphDashboardRoute,
    ),
)

@Composable
fun BottomBarSection() {
    val bottomNavGraph = LocalGraphBottom.current
    val dashboardNavGraph = LocalGraphDashboard.current

    val selectedBottomTab = bottomNavGraph.lastOrNull()
    val selectedDashboardTab = dashboardNavGraph.lastOrNull()
    val dashboardRoot = dashboardNavGraph.firstOrNull()

    bottomBarItems.forEach { tab ->
        val isSelected = selectedBottomTab == tab.route
        MkShortNavigationBarItem(
            selected = isSelected,
            onClick = {
                when {
                    tab.route == GraphDashboardRoute &&
                        isSelected &&
                        selectedDashboardTab != dashboardRoot -> {
                        dashboardNavGraph.clearExceptRoot()
                    }

                    else -> bottomNavGraph.switchTo(tab.route)
                }
            },
            icon = { MkIcon(imageVector = tab.icon) },
            label = { MkText(text = tab.label, maxLines = 1) },
        )
    }
}
