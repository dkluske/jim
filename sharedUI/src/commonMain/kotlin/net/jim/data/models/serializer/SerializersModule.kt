package net.jim.data.models.serializer

import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import net.jim.data.models.JsonExerciseType
import net.jim.data.models.PhysicalJsonExercise
import net.jim.data.models.WorkoutEntryExercise
import net.jim.data.models.WorkoutPlanExercise

val jimSerializersModule = SerializersModule {
    fun PolymorphicModuleBuilder<JsonExerciseType>.registerJsonExerciseTypeSubclasses() {
        subclass(PhysicalJsonExercise::class)
    }

    fun PolymorphicModuleBuilder<WorkoutPlanExercise.RepetitionInterval>.registerRepetitionIntervalSubclasses() {
        subclass(WorkoutPlanExercise.Repeating::class)
        subclass(WorkoutPlanExercise.Timed::class)
    }

    fun PolymorphicModuleBuilder<WorkoutEntryExercise.ExerciseExecution>.registerExerciseExecutionSubclasses() {
        subclass(WorkoutEntryExercise.WeighedRepetitionExerciseExecution::class)
    }

    polymorphic(JsonExerciseType::class) {
        registerJsonExerciseTypeSubclasses()
    }

    polymorphic(WorkoutPlanExercise.RepetitionInterval::class) {
        registerRepetitionIntervalSubclasses()
    }

    polymorphic(WorkoutEntryExercise.ExerciseExecution::class) {
        registerExerciseExecutionSubclasses()
    }
}