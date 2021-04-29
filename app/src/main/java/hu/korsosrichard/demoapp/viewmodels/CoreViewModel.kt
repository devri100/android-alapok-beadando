package hu.korsosrichard.demoapp.viewmodels

import androidx.lifecycle.*
import hu.korsosrichard.demoapp.repositories.CoreRepository
import hu.korsosrichard.demoapp.utils.MyResult
import kotlinx.coroutines.Dispatchers

abstract class CoreViewModel<T>(private val repository: CoreRepository<T>) : ViewModel() {

    protected val _id = MutableLiveData<Long>()

    private val _list = MediatorLiveData<MyResult<List<T>>>()

    private val _action = MediatorLiveData<MyResult<T>>()

    val list: LiveData<MyResult<List<T>>> get() = _list

    val data = Transformations.switchMap(_id) {
        liveData(viewModelScope.coroutineContext) {
            emitSource(repository.getById(it))
        }
    }

    fun insert(item: T): LiveData<MyResult<T>> = liveData {
        emitSource(repository.insert(item))
    }

    fun update(item: T): LiveData<MyResult<T>> = liveData {
        emitSource(repository.update(item))
    }

    fun delete(item: T): LiveData<MyResult<T>> = liveData {
        emitSource(repository.delete(item))
    }

    var id: Long?
        get() = _id.value
        set(value) {
            if (_id.value != value) {
                _id.value = value
            }
        }

    fun loadList() {
        if (_list.value == null) {
            _list.addSource(repository.getAll()) {
                _list.value = it
            }
        }
    }
}
