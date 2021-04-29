package hu.korsosrichard.demoapp.models

import androidx.room.Embedded
import androidx.room.Relation

data class BookAndAuthor(
    @Embedded
    val book: Book,
    @Relation(parentColumn = "authorId", entityColumn = "id")
    val author: Author
)