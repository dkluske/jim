package net.jim.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import net.jim.sqldelight.JimRuntimeDatabase

actual class DatabaseDriverManager {
    actual fun createDriver(dbName: String): SqlDriver {
        return NativeSqliteDriver(JimRuntimeDatabase.Schema, dbName)
    }
}