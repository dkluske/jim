package net.jim.ui.routes.util

import net.jim.Root
import net.jim.ui.Route

interface ViewModel {
    val root: Root
    val routeOrigin: Route
}