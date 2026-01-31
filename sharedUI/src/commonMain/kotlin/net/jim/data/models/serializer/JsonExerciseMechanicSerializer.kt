package net.jim.data.models.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.jim.data.models.JsonExercise

object JsonExerciseMechanicSerializer : KSerializer<JsonExercise.MechanicEnum> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("JsonExercise.MechanicEnum", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: JsonExercise.MechanicEnum
    ) {
        encoder.encodeString(value.name.lowercase().replace('_', ' '))
    }

    override fun deserialize(decoder: Decoder): JsonExercise.MechanicEnum {
        return JsonExercise.MechanicEnum.valueOf(decoder.decodeString().uppercase().replace(' ', '_'))
    }
}