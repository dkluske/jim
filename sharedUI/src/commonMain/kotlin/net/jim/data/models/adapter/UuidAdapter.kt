package net.jim.data.models.adapter

import app.cash.sqldelight.ColumnAdapter
import kotlin.uuid.Uuid

class UuidAdapter : ColumnAdapter<Uuid, String> {
    override fun decode(databaseValue: String): Uuid {
        return Uuid.parse(databaseValue)
    }

    override fun encode(value: Uuid): String {
        return value.toString()
    }
}