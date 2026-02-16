package net.jim.data.table

import net.jim.data.models.Entity
import net.jim.data.models.exception.NoDatabaseException
import net.jim.sqldelight.JimRuntimeDatabase
import kotlin.concurrent.atomics.AtomicReference

/**
 * Central Interface for Database Table Implementations
 * ID: The Object to be set as the ID of the Entity in the Table
 * E: The Entity, that shall be managed by that table
 */
sealed interface Table<ID : Any, E : Entity<ID, *>> {
    val database: AtomicReference<JimRuntimeDatabase?>

    /**
     * Queries an Entity by it's ID
     * @param id The ID of the Entity to query
     * @return The desired Entity
     */
    fun getById(id: ID): E

    /**
     * Queries all Entities from the Table
     * @return A List of all existing Entities
     */
    fun getAll(): List<E>

    /**
     * Saves an Entity to the database
     * @param entity The Entity to save
     * @return The new inserted Entity
     */
    fun save(entity: E): E

    /**
     * Deletes an Entity by their ID
     * @param id
     */
    fun delete(id: ID)

    /**
     * Overrides an entity by it's id
     * @param id The ID of the Entity
     * @param entity The entity data to set
     * @return The new Entity instance
     */
    fun update(id: ID, entity: E): E

    /**
     * Central method for querying the database instance for queries.
     * Please use this method instead of using the nullable database atomic reference.
     * @return [JimRuntimeDatabase]
     */
    fun getDatabase(): JimRuntimeDatabase {
        return database.load() ?: throw NoDatabaseException(clazz = this::class)
    }

    /**
     * Central method to initialize the Table by setting the database
     * @param database The database instance from SQLDelight to be set to the atomic reference
     */
    fun setDatabase(database: JimRuntimeDatabase) {
        this.database.store(database)
    }
}