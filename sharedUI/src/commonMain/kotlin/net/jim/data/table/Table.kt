package net.jim.data.table

import net.jim.data.models.Entity

sealed interface Table<ID : Any, E : Entity<ID>> {
    val name: String

    fun getById(id: ID): E

    fun getAll(): List<E>

    fun save(entity: E): E

    fun delete(id: ID)

    fun update(id: ID, entity: E): E
}