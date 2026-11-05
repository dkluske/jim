package net.jim.data.models

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class Identifier(
    val id: Uuid,
    val name: String
)
