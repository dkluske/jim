package net.jim.data.models.exception

import kotlin.reflect.KClass

class NoDatabaseException(clazz: KClass<*>) : RuntimeException("Database was not initialized properly for $clazz.")