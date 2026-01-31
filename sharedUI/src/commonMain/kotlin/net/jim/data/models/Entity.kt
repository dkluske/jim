package net.jim.data.models

interface Entity<ID : Any> {
    val id: ID
}