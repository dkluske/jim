package net.jim.data.models.serializer

import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import net.jim.data.models.JsonExerciseType
import net.jim.data.models.PhysicalJsonExercise

val jimSerializersModule = SerializersModule {
    fun PolymorphicModuleBuilder<JsonExerciseType>.registerJsonExerciseTypeSubclasses() {
        subclass(PhysicalJsonExercise::class)
    }

    polymorphic(JsonExerciseType::class) {
        registerJsonExerciseTypeSubclasses()
    }
}