package net.jim.data.models.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.jim.data.models.JsonExerciseType

object JsonExerciseForceSerializer : KSerializer<JsonExerciseType.ForceEnum> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("JsonExercise.ForceEnum", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: JsonExerciseType.ForceEnum
    ) {
        encoder.encodeString(value.name.lowercase().replace('_', ' '))
    }

    override fun deserialize(decoder: Decoder): JsonExerciseType.ForceEnum {
        return JsonExerciseType.ForceEnum.valueOf(decoder.decodeString().uppercase().replace(' ', '_'))
    }
}