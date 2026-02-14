package net.jim.data.models.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.jim.data.models.JsonExerciseType

object JsonExerciseMuscleSerializer : KSerializer<JsonExerciseType.MuscleEnum> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("JsonExerciseType.MuscleEnum", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: JsonExerciseType.MuscleEnum
    ) {
        encoder.encodeString(value.name.lowercase().replace('_', ' '))
    }

    override fun deserialize(decoder: Decoder): JsonExerciseType.MuscleEnum {
        return JsonExerciseType.MuscleEnum.valueOf(decoder.decodeString().uppercase().replace(' ', '_'))
    }
}