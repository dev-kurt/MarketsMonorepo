package com.devkurt.markets.ui.api.motion

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

private const val DURATION = 500

object MkEnterTransition {
    val slideInRight = slideInHorizontally(tween(DURATION)) { it }
    val slideInLeft = slideInHorizontally(tween(DURATION)) { -it }
    val slideInVertically: EnterTransition = slideInVertically(tween(DURATION)) { it * 2 }
    val slideInTop: EnterTransition = slideInVertically(tween(DURATION)) { -it }
    val slideInBottom: EnterTransition = slideInVertically(tween(DURATION)) { it }
    val fadeIn: EnterTransition = fadeIn(tween(DURATION))
    val expandVertically: EnterTransition = expandVertically(tween(DURATION))
    val scaleIn = scaleIn(tween(DURATION), transformOrigin = TransformOrigin.Center)
    val none = EnterTransition.None
}

object MkExitTransition {
    val slideOutRight = slideOutHorizontally(tween(DURATION)) { it }
    val slideOutLeft = slideOutHorizontally(tween(DURATION)) { -it }
    val slideOutTop: ExitTransition = slideOutVertically(tween(DURATION)) { -it }
    val slideOutBottom: ExitTransition = slideOutVertically(tween(DURATION)) { it }
    val fadeOut: ExitTransition = fadeOut(tween(DURATION))
    val shrinkVertically: ExitTransition = shrinkVertically(tween(DURATION))
    val scaleOut = scaleOut(tween(DURATION), transformOrigin = TransformOrigin.Center)
    val none = ExitTransition.None
}

object MkContentSizeTransition {
    val default: FiniteAnimationSpec<IntSize> = tween(DURATION)
}

object MkContentTransform {
    fun verticalReel(
        durationMillis: Int,
        easing: Easing = LinearEasing,
    ): ContentTransform {
        val slideSpec = tween<IntOffset>(durationMillis = durationMillis, easing = easing)
        return slideInVertically(slideSpec) { fullHeight -> -fullHeight } togetherWith
                slideOutVertically(slideSpec) { fullHeight -> fullHeight }
    }
}
