package hu.korsosrichard.demoapp.viewmodels

import androidx.lifecycle.*
import hu.korsosrichard.demoapp.models.Book
import hu.korsosrichard.demoapp.models.BookAndAuthor
import hu.korsosrichard.demoapp.repositories.BookRepository
import hu.korsosrichard.demoapp.utils.MyResult
import javax.inject.Inject

class BookViewModel @Inject constructor(val bookRepository: BookRepository) :
    CoreViewModel<Book>(bookRepository) {

    private val _bookAndAuthorList = MediatorLiveData<MyResult<List<BookAndAuthor>>>()

    val bookAndAuthorList: LiveData<MyResult<List<BookAndAuthor>>> get() = _bookAndAuthorList

    val bookAndAuthor = Transformations.switchMap(_id) {
        liveData(viewModelScope.coroutineContext) {
            emitSource(bookRepository.getWithAuthorById(it))
        }
    }

    fun loadBookAndAuthorList() {
        if(_bookAndAuthorList.value == null){
            _bookAndAuthorList.addSource(bookRepository.getAllWithAuthor()) {
                _bookAndAuthorList.value = it
            }
        }
    }
}

