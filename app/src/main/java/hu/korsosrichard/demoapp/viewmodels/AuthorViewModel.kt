package hu.korsosrichard.demoapp.viewmodels

import hu.korsosrichard.demoapp.models.Author
import hu.korsosrichard.demoapp.repositories.AuthorRepository
import javax.inject.Inject

class AuthorViewModel @Inject constructor(authorRepository: AuthorRepository) :
    CoreViewModel<Author>(authorRepository)