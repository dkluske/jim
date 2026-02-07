package net.jim.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import net.jim.sqldelight.JimRuntimeDatabase
import java.util.*

actual class DatabaseDriverManager {
    actual fun createDriver(dbName: String): SqlDriver {
        val dbUrl = "jdbc:sqlite:$dbName"
        return JdbcSqliteDriver(dbUrl, Properties(), JimRuntimeDatabase.Schema)
    }
}