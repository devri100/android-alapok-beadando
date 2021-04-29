package hu.korsosrichard.demoapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import hu.korsosrichard.demoapp.db.BookDao
import hu.korsosrichard.demoapp.models.Book
import hu.korsosrichard.demoapp.models.BookAndAuthor
import hu.korsosrichard.demoapp.utils.MyResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepository @Inject constructor(val bookDao: BookDao) : CoreRepository<Book>(bookDao) {

    fun getAllWithAuthor(): LiveData<MyResult<List<BookAndAuthor>>> {
        return load(bookDao.selectAllWitAuthor().map { list ->
            list.sortedWith(
                compareBy<BookAndAuthor> { it.book.title }
                    .thenBy { it.author.firstName }
                    .thenBy { it.author.lastName }
            )
        })
    }

    fun getWithAuthorById(id: Long): LiveData<MyResult<BookAndAuthor>> {
        return load(bookDao.selectWithAuthorById(id))
    }

}