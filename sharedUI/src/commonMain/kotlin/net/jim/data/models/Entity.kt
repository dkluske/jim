package net.jim.data.models

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Polymorphic
@SerialName("Entity")
sealed interface Entity<ID : Any, DB : Any> {
    val id: ID

    fun toDB(): DB
}