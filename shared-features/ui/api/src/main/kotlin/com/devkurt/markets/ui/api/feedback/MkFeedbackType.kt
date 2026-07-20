package com.devkurt.markets.ui.api.feedback

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector
import com.devkurt.markets.ui.api.R

enum class MkFeedbackType(
    @StringRes val labelRes: Int,
    val icon: ImageVector,
) {
    Error(R.string.mk_feedback_error, Icons.Filled.Warning),
    Warning(R.string.mk_feedback_warning, Icons.Filled.Warning),
    Info(R.string.mk_feedback_information, Icons.Filled.Info),
    Success(R.string.mk_feedback_success, Icons.Filled.CheckCircle),
}
