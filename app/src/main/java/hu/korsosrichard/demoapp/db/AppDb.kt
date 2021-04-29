package hu.korsosrichard.demoapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.korsosrichard.demoapp.models.Author
import hu.korsosrichard.demoapp.models.Book

@Database(
    entities = [Book::class, Author::class],
    version = 11,
    exportSchema = false
)
@TypeConverters(DateTypeConverter::class)
abstract class AppDb : RoomDatabase() {

    abstract fun bookDao(): BookDao
    abstract fun authorDao(): AuthorDao

}