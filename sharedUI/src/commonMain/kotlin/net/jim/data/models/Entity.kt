package net.jim.data.models

interface Entity<ID : Any, DB : Any> {
    val id: ID

    fun toDB(): DB
}