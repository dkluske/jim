package net.jim.data.models.adapter

import app.cash.sqldelight.ColumnAdapter
import kotlinx.serialization.json.Json
import kotlin.time.Instant

class InstantAdapter(val json: Json) : ColumnAdapter<Instant, String> {
    override fun decode(databaseValue: String): Instant {
        return json.decodeFromString<Instant>(databaseValue)
    }

    override fun encode(value: Instant): String {
        return json.encodeToString(value)
    }
}