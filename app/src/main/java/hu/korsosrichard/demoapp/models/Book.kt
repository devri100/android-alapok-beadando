package hu.korsosrichard.demoapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(indices = [Index(value = ["authorId"])])
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val authorId: Long,
    val title: String,
    val description: String?,
    val isbn: String?,
    val publication: Int?
) : Parcelable