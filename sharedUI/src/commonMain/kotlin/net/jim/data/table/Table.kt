package net.jim.data.table

import net.jim.data.NoDatabaseException
import net.jim.data.models.Entity
import net.jim.sqldelight.JimRuntimeDatabase
import kotlin.concurrent.atomics.AtomicReference

sealed interface Table<ID : Any, E : Entity<ID>> {
    val database: AtomicReference<JimRuntimeDatabase?>

    fun getById(id: ID): E

    fun getAll(): List<E>

    fun save(entity: E): E

    fun delete(id: ID)

    fun update(id: ID, entity: E): E

    fun getDatabase(): JimRuntimeDatabase {
        return database.load() ?: throw NoDatabaseException()
    }
}