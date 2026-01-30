package net.jim.components.utils

import androidx.compose.runtime.Composable

@Composable
fun <T : Any> JimListWrapper(
    list: List<T>,
    wrap: Int,
    onEmpty: (@Composable () -> Unit)? = null,
    onShowMore: (@Composable () -> Unit)? = null,
    entityModification: @Composable (T) -> Unit
) {
    if (list.isNotEmpty()) {
        list.take(wrap).forEach {
            entityModification(it)
        }
        onShowMore?.invoke()
    } else {
        onEmpty?.invoke()
    }
}