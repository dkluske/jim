package net.jim.data.models

sealed interface EntityConvertable<DB : Any, E : Any> {
    fun fromDB(db: DB): E
}