package hu.korsosrichard.demoapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import hu.korsosrichard.demoapp.models.Book
import hu.korsosrichard.demoapp.models.BookAndAuthor

@Dao
interface BookDao: CoreDao<Book> {
    @Query("SELECT * FROM Book")
    override fun selectAll(): LiveData<List<Book>>

    @Query("SELECT * FROM Book WHERE id = :id")
    override fun selectById(id: Long): LiveData<Book>

    @Query("SELECT * FROM Book")
    fun selectAllWitAuthor(): LiveData<List<BookAndAuthor>>

    @Query("SELECT * FROM Book WHERE id = :id")
    fun selectWithAuthorById(id: Long): LiveData<BookAndAuthor>
}