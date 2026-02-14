package net.jim.data

import app.cash.sqldelight.db.SqlDriver
import kotlinx.serialization.json.Json
import net.jim.data.models.adapter.TableAdapters
import net.jim.data.table.JsonExerciseTable
import net.jim.sqldelight.JimRuntimeDatabase

expect class DatabaseDriverManager {
    fun createDriver(dbName: String): SqlDriver
}

object JimDatabaseManager {
    val json: Json = Json {
        ignoreUnknownKeys = true
    }

    fun initTables(database: JimRuntimeDatabase) {
        JsonExerciseTable.setDatabase(database)
    }

    fun createDatabase(driverFactory: DatabaseDriverManager): JimRuntimeDatabase {
        val driver = driverFactory.createDriver("jim-runtime.db")
        return JimRuntimeDatabase(
            driver = driver,
            json_exercisesAdapter = TableAdapters.jsonExerciseAdapter
        )
    }
}