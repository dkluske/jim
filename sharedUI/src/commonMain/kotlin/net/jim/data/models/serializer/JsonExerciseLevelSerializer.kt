package net.jim.data.models.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.jim.data.models.JsonExercise

object JsonExerciseLevelSerializer : KSerializer<JsonExercise.LevelEnum> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("JsonExercise.LevelEnum", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: JsonExercise.LevelEnum
    ) {
        encoder.encodeString(value.name.lowercase().replace('_', ' '))
    }

    override fun deserialize(decoder: Decoder): JsonExercise.LevelEnum {
        return JsonExercise.LevelEnum.valueOf(decoder.decodeString().uppercase().replace(' ', '_'))
    }
}