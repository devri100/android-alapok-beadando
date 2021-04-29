package hu.korsosrichard.demoapp.repositories

import hu.korsosrichard.demoapp.db.AuthorDao
import hu.korsosrichard.demoapp.models.Author
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorRepository @Inject constructor(authorDao: AuthorDao): CoreRepository<Author>(authorDao)