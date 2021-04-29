package hu.korsosrichard.demoapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hu.korsosrichard.demoapp.viewmodels.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hu.korsosrichard.demoapp.viewmodels.AuthorViewModel
import hu.korsosrichard.demoapp.viewmodels.BookViewModel

@Suppress("UNUSED")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BookViewModel::class)
    abstract fun bindBookViewModel(viewModel: BookViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthorViewModel::class)
    abstract fun bindAuthorViewModel(viewModel: AuthorViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory



}