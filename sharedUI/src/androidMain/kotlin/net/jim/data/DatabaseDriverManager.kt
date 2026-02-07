package net.jim.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import net.jim.sqldelight.JimRuntimeDatabase

actual class DatabaseDriverManager(val context: Context) {
    actual fun createDriver(dbName: String): SqlDriver {
        return AndroidSqliteDriver(JimRuntimeDatabase.Schema, context, dbName)
    }
}