package hu.korsosrichard.demoapp.repositories

import androidx.lifecycle.*
import hu.korsosrichard.demoapp.db.CoreDao
import hu.korsosrichard.demoapp.utils.MyResult
import kotlinx.coroutines.*
import java.lang.Exception

abstract class CoreRepository<T>(val dao: CoreDao<T>) {

    private fun action(item: T, callback: suspend () -> T) = liveData {
        emit(MyResult.loading(item))
        try{
            val newItem = callback()
            emit(MyResult.success(newItem))
        } catch (e: Exception) {
            emit(MyResult.failure(e, item))
        }
    }

    suspend fun insert(item: T) = action(item) {
        dao.insert(item)
        item
    }

    suspend fun update(item: T) = action(item) {
        dao.update(item)
        item
    }

    suspend fun delete(item: T) = action(item) {
        dao.delete(item)
        item
    }

    protected fun <V> load(source: LiveData<V>) =
        liveData<MyResult<V>>(Dispatchers.IO) {
            emit(MyResult.loading(null))
//            delay(3000)
            emitSource(source.map { MyResult.success(it) })
        }

    fun getAll(): LiveData<MyResult<List<T>>> {
        return load(dao.selectAll())
    }

    fun getById(id: Long): LiveData<MyResult<T>> {
        return load(dao.selectById(id))
    }

}