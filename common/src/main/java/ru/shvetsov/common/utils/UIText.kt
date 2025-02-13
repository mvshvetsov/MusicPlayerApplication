package ru.shvetsov.common.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UIText {
    data class RemoteString(val message: String) : UIText()
    class StringResource(@StringRes val resId: Int, vararg val args: Any) : UIText()
    data object EmptyString : UIText()

    @Composable
    fun asString(): String {
        return when (this) {
            is RemoteString -> message
            is StringResource -> stringResource(id = resId, *args)
            is EmptyString -> ""
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is RemoteString -> message
            is StringResource -> context.getString(resId, *args)
            is EmptyString -> ""
        }
    }
}