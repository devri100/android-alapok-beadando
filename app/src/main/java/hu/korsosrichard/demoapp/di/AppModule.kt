package hu.korsosrichard.demoapp.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.Observer
import androidx.room.Room
import com.github.javafaker.Faker
import hu.korsosrichard.demoapp.Constants.BASE_URL
import hu.korsosrichard.demoapp.api.AppService
import hu.korsosrichard.demoapp.api.BooleanTypeAdapter
import hu.korsosrichard.demoapp.db.AppDb
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import hu.korsosrichard.demoapp.db.AuthorDao
import hu.korsosrichard.demoapp.db.BookDao
import hu.korsosrichard.demoapp.models.Author
import hu.korsosrichard.demoapp.models.Book
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton
import kotlin.math.roundToInt
import kotlin.math.roundToLong

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): AppService {
        return retrofit.create(AppService::class.java)
    }

    @Singleton
    @Provides
    fun providerGson(): Gson {
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .registerTypeAdapter(
                Boolean::class.java,
                BooleanTypeAdapter()
            )
            //.registerTypeAdapter(Boolean::class.java, BooleanTypeAdapter())
            //.excludeFieldsWithoutExposeAnnotation()
            .setLenient()
            .create()
    }

    @Singleton
    @Provides
    fun providerRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            //.addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDb {

        val db = Room.databaseBuilder(app, AppDb::class.java, "appDB")
            .fallbackToDestructiveMigration()
            //.allowMainThreadQueries()
            .build()

        val faker = Faker();

        val liveData = db.authorDao().selectAll()
        lateinit var observer: Observer<List<Author>>
        observer = Observer {
            liveData.removeObserver(observer)
            if (it.isEmpty()) {
                GlobalScope.launch {
                    for (i in 0..100) {
                        db.authorDao().insert(
                            Author(
                                0,
                                faker.name().firstName(),
                                faker.name().lastName(),
                                faker.date().birthday()
                            )
                        )
                    }

                    for (i in 0..300) {
                        db.bookDao().insert(
                            Book(
                                0,
                                (Math.random() * 100).roundToLong() + 1,
                                faker.book().title(),
                                faker.lorem().paragraph(),
                                faker.number().randomNumber(10, true).toString(),
                                faker.number().numberBetween(1300, 2000)
                            )
                        )
                    }
                }
            }
        }

        liveData.observeForever(observer)

        return db
    }

    @Singleton
    @Provides
    fun provideApplicationContext(app: Application): Context = app.applicationContext

    @Provides
    fun provideBookDao(db: AppDb): BookDao = db.bookDao()

    @Provides
    fun provideAuthorDao(db: AppDb): AuthorDao = db.authorDao()
}