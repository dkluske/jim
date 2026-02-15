package net.jim.data

import app.cash.sqldelight.db.SqlDriver
import kotlinx.serialization.json.Json
import net.jim.data.models.adapter.TableAdapters
import net.jim.data.models.serializer.jimSerializersModule
import net.jim.data.table.JsonExerciseTable
import net.jim.data.table.WorkoutPlanPartTable
import net.jim.data.table.WorkoutPlanTable
import net.jim.sqldelight.JimRuntimeDatabase

/**
 * Helper class for Multiplatform implementations of the database driver
 */
expect class DatabaseDriverManager {
    /**
     * Create platform specific database driver
     * @param dbName The name of the database file
     * @return The platform specific database driver
     */
    fun createDriver(dbName: String): SqlDriver
}

/**
 * Object for central database operations
 */
object JimDatabaseManager {
    /**
     * Central JSON for serialization
     */
    val json: Json = Json {
        ignoreUnknownKeys = true
        serializersModule = jimSerializersModule
    }

    /**
     * Method to initialize the DAOs with the database object
     * @param database The database object to inject to the DAOs
     */
    fun initTables(database: JimRuntimeDatabase) {
        JsonExerciseTable.setDatabase(database)
        WorkoutPlanTable.setDatabase(database)
        WorkoutPlanPartTable.setDatabase(database)
    }

    /**
     * Creates the database object for the tables to be initialized
     * @param driverFactory The platform specific factory to initialize the database object
     * @return [JimRuntimeDatabase] as the central database object
     */
    fun createDatabase(driverFactory: DatabaseDriverManager): JimRuntimeDatabase {
        val driver = driverFactory.createDriver("jim-runtime.db")
        return JimRuntimeDatabase(
            driver = driver,
            json_exercisesAdapter = TableAdapters.physicalJsonExerciseAdapter,
            workout_plansAdapter = TableAdapters.workoutPlansAdapter,
            workout_plan_partsAdapter = TableAdapters.workoutPlanPartsAdapter
        )
    }
}