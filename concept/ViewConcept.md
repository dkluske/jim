# View Flow Concept

This file shall provide information on how the navigation and app usage workflow is imagined.
The following diagram shows an abstract concept of the interaction workflow.

~~~mermaid
flowchart TD
    ROOT[fa:fa-code App] -->|Initialization Stuff| DATABASE(fa:fa-database Database)
    ROOT -->|Initialization Stuff| NAVSTACK(fa:fa-arrow-left Nav Stack)
    ROOT -->|Initialization Stuff| THEME(fa:fa-palette Theme)
    ROOT -->|Initialization Stuff| EXERCISE_CHANGESETS(fa:fa-table Exercise Changesets)

    DATABASE --> HOME_VIEW(fa:fa-home Home)
    NAVSTACK --> HOME_VIEW
    THEME --> HOME_VIEW
    
    HOME_VIEW --> WORKOUT_PLAN_PART_PICKER_MODAL(fa:fa-play Choose Plan to run)
    HOME_VIEW --> EDIT_WORKOUT_PLAN_VIEW(fa:fa-wrench Create or Edit Workout Plan)
    HOME_VIEW --> CHARTS_VIEW(fa:fa-bar-chart Statistics)
    HOME_VIEW --> SETTINGS_VIEW(fa:fa-gear Settings)

    WORKOUT_PLAN_PART_PICKER_MODAL --> WORKOUT_PLAN_PART_RUN_VIEW(Workout Plan Part Run)
    
    EDIT_WORKOUT_PLAN_VIEW --> EDIT_WORKOUT_PLAN_PART_MODAL(Create or Edit Workout Plan Part)

    CHARTS_VIEW --> MUSCLE_GROUP_CHARTS[Muscle Group Stats]
    CHARTS_VIEW --> WORKOUT_PLAN_CHARTS[Workout Plan Stats]

    SETTINGS_VIEW --> RESET_APP[Reset App]
    SETTINGS_VIEW --> DEBUG_INFO[Debug Info]
    SETTINGS_VIEW --> EXERCISE_UPDATE_STATUS[Exercise Update Status]
~~~