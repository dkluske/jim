package net.jim.data.models.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.jim.data.models.JsonExercise

object JsonExerciseCategorySerializer : KSerializer<JsonExercise.CategoryEnum> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("JsonExercise.CategoryEnum", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: JsonExercise.CategoryEnum
    ) {
        encoder.encodeString(value.name.lowercase().replace('_', ' '))
    }

    override fun deserialize(decoder: Decoder): JsonExercise.CategoryEnum {
        return JsonExercise.CategoryEnum.valueOf(decoder.decodeString().uppercase().replace(' ', '_'))
    }
}