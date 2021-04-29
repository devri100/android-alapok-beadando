package hu.korsosrichard.demoapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.korsosrichard.demoapp.utils.AdapterModel
import hu.korsosrichard.demoapp.utils.defaultDateFormat
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Entity
@Parcelize
data class Author(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val firstName: String,
    val lastName: String,
    val birthday: Date?
) : AdapterModel, Parcelable {
    override fun getAdapterId(): Long = id

    override fun getAdapterText(): String = "$firstName $lastName"

    val wholeName: String get() = "$firstName $lastName"

    val birthdayString: String
        get() = if (birthday == null) "" else defaultDateFormat().format(birthday)
}