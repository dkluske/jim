package net.jim.data

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverManager {
    fun createDriver(dbName: String): SqlDriver
}

object JimDatabaseManager {
    fun createDatabase(driverFactory: DatabaseDriverManager) {
        val driver = driverFactory.createDriver("jim-runtime.db")
    }
}