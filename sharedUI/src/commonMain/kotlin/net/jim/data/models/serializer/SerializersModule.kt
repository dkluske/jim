package net.jim.data.models.serializer

import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import net.jim.data.models.*

val jimSerializersModule = SerializersModule {
    fun PolymorphicModuleBuilder<Entity<*, *>>.registerEntitySubclasses() {
        subclass(WorkoutEntry::class)
        subclass(WorkoutEntryExercise::class)
        subclass(WorkoutPlan::class)
        subclass(WorkoutPlanPart::class)
    }

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

    polymorphic(Entity::class) {
        registerEntitySubclasses()
        registerJsonExerciseTypeSubclasses()
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