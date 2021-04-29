package hu.korsosrichard.demoapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface CoreDao<T> {

    @Insert
    suspend fun insert(item: T)

    @Update
    suspend fun update(item: T)

    @Delete
    suspend fun delete(item: T)

    fun selectAll(): LiveData<List<T>>

    fun selectById(id: Long): LiveData<T>
}