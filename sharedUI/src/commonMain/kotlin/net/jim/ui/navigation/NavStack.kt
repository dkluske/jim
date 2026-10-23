package net.jim.ui.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.StateObject
import kotlinx.coroutines.sync.Mutex
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import net.jim.ui.BottomSheet
import net.jim.ui.Dialog
import net.jim.ui.Route

@Serializable
open class NavStack<T : Route>(private val base: @Contextual SnapshotStateList<T>) : MutableList<T>,
    StateObject by base,
    RandomAccess {
    private val mutex = Mutex()

    constructor(vararg elements: T) : this(base = mutableStateListOf(*elements)) {
        if (base.any { it is Dialog }) {
            base.removeAll { it is Dialog }
        }
        if (elements.any { it is Dialog }) {
            base.addAll(elements.filterNot { it is Dialog } + elements.last { it is Dialog })
        } else {
            base.addAll(elements)
        }

        if (base.any { it is BottomSheet }) {
            base.removeAll { it is BottomSheet }
        }
        if (elements.any { it is BottomSheet }) {
            base.addAll(elements.filterNot { it is BottomSheet } + elements.last { it is BottomSheet })
        } else {
            base.addAll(elements)
        }
    }

    private inline fun <R> sync(crossinline action: () -> R): R {
        while (!mutex.tryLock()) {
            // wait for unlocking
        }
        try {
            return action()
        } finally {
            mutex.unlock()
        }
    }

    override val size: Int get() = sync { base.size }

    override fun get(index: Int): T = sync { base[index] }

    @Deprecated(
        message = "Use plusAssign instead.",
        level = DeprecationLevel.ERROR
    )
    override fun add(element: T): Boolean = sync {
        when (element) {
            is Dialog -> {
                base.removeAll { it is Dialog }
                base.add(element)
            }

            is BottomSheet -> {
                base.removeAll { it is BottomSheet }
                base.add(element)
            }

            is Route.MainRoute -> {
                val lastRootView = base.findLast { it is Route.MainRoute }
                if (lastRootView == element) {
                    return@sync false
                }

                Snapshot.withMutableSnapshot {
                    base.clear()
                    base.add(element)
                }
                true
            }

            is Route.SecondaryRoute -> {
                base.add(element)
                true
            }

            else -> {
                false
            }
        }
    }

    @Deprecated(
        message = "Do not use.",
        level = DeprecationLevel.HIDDEN
    )
    override fun add(index: Int, element: T): Unit = sync {
        base.add(index, element)
    }

    @Deprecated(
        message = "Do not use.",
        level = DeprecationLevel.HIDDEN
    )
    override fun addAll(elements: Collection<T>): Boolean = sync {
        @Suppress("DEPRECATION_ERROR")
        elements.all { add(it) }
    }

    @Deprecated(
        message = "Do not use.",
        level = DeprecationLevel.HIDDEN
    )
    override fun addAll(index: Int, elements: Collection<T>): Boolean = sync {
        base.addAll(index, elements)
    }

    @Deprecated(
        message = "Do not use.",
        level = DeprecationLevel.HIDDEN
    )
    override fun clear() = sync {
        base.clear()
    }

    override fun iterator(): MutableIterator<T> = sync {
        base.toMutableList().iterator()
    }

    override fun listIterator(): MutableListIterator<T> = sync {
        base.toMutableList().listIterator()
    }

    override fun listIterator(index: Int): MutableListIterator<T> = sync {
        base.toMutableList().listIterator(index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> = sync {
        base.toMutableList().subList(fromIndex, toIndex)
    }

    override fun set(index: Int, element: T): T = sync {
        base.set(index, element)
    }

    override fun remove(element: T): Boolean = sync {
        if (element is Route.MainRoute) {
            return@sync false
        }
        base.remove(element)
    }

    override fun removeAt(index: Int): T = sync {
        if (index == 0) {
            return@sync base[index]
        }
        base.removeAt(index)
    }

    override fun removeAll(elements: Collection<T>): Boolean = sync {
        if (elements.any { it is Route.MainRoute }) {
            return@sync false
        }
        base.removeAll(elements)
    }

    fun removeAll(predicate: (T) -> Boolean): Boolean = sync {
        val toRemove = base.filter { predicate(it) && it !is Route.MainRoute }
        base.removeAll(toRemove)
    }

    fun removeLastOrNull(): T? = sync {
        if (base.isEmpty() && base.filterIsInstance<Route.MainRoute>().size > 1) {
            return@sync base.removeLast()
        } else {
            return@sync null
        }
    }

    override fun retainAll(elements: Collection<T>): Boolean = sync {
        if (elements.none { it is Route.MainRoute }) {
            return@sync false
        }
        base.retainAll(elements)
    }

    override fun contains(element: T): Boolean = sync {
        base.contains(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean = sync {
        base.containsAll(elements)
    }

    override fun indexOf(element: T): Int = sync {
        base.indexOf(element)
    }

    override fun lastIndexOf(element: T): Int = sync {
        base.lastIndexOf(element)
    }

    override fun isEmpty(): Boolean = sync {
        base.isEmpty()
    }

    operator fun plusAssign(element: T) {
        @Suppress("DEPRECATION_ERROR") // here it is ok :)
        add(element)
    }

    fun forEach(action: (T) -> Unit) = sync {
        base.forEach(action)
    }

    fun any(predicate: (T) -> Boolean): Boolean = sync {
        base.any(predicate)
    }

    fun none(predicate: (T) -> Boolean): Boolean = sync {
        base.none(predicate)
    }

    fun filter(predicate: (T) -> Boolean): List<T> = sync {
        base.filter(predicate)
    }

    fun <R> map(transform: (T) -> R): List<R> = sync {
        base.map(transform)
    }

    fun find(predicate: (T) -> Boolean): T? = sync {
        base.find(predicate)
    }

    fun findLast(predicate: (T) -> Boolean): T? = sync {
        base.findLast(predicate)
    }

    fun first(): T = sync { base.first() }

    fun firstOrNull(): T? = sync { base.firstOrNull() }

    fun first(predicate: (T) -> Boolean): T = sync { base.first(predicate) }

    fun firstOrNull(predicate: (T) -> Boolean): T? = sync { base.firstOrNull(predicate) }

    fun last(): T = sync { base.last() }

    fun lastOrNull(): T? = sync { base.lastOrNull() }

    fun last(predicate: (T) -> Boolean): T = sync { base.last(predicate) }

    fun lastOrNull(predicate: (T) -> Boolean): T? = sync { base.lastOrNull(predicate) }

    fun toList(): List<T> = sync { base.toList() }

}