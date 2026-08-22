package net.jim.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("JsonExerciseChangeset")
data class JsonExerciseChangeset(
    val jsonExercises: List<JsonExerciseType>,
    val dbOperationType: DBOperationType
) {
    @Serializable
    enum class DBOperationType {
        INSERT,
        UPDATE,
        DELETE;
    }
}
