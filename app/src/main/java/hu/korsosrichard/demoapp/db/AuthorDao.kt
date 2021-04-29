package hu.korsosrichard.demoapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import hu.korsosrichard.demoapp.models.Author
import hu.korsosrichard.demoapp.models.Book

@Dao
interface AuthorDao: CoreDao<Author> {
    @Query("SELECT * FROM Author ORDER BY firstName, lastName")
    override fun selectAll(): LiveData<List<Author>>

    @Query("SELECT * FROM Author WHERE id = :id")
    override fun selectById(id: Long): LiveData<Author>
}