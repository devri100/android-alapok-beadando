package hu.korsosrichard.demoapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import hu.korsosrichard.demoapp.ui.author.AuthorFormFragment
import hu.korsosrichard.demoapp.ui.author.AuthorListFragment
import hu.korsosrichard.demoapp.ui.book.BookDetailsFragment
import hu.korsosrichard.demoapp.ui.book.BookFormFragment
import hu.korsosrichard.demoapp.ui.book.BookListFragment

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeBookListFragment(): BookListFragment

    @ContributesAndroidInjector
    abstract fun contributeBookFormFragment(): BookFormFragment

    @ContributesAndroidInjector
    abstract fun contributeBookDetailsFragment(): BookDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeAuthorListFragment(): AuthorListFragment

    @ContributesAndroidInjector
    abstract fun contributeAuthorFormFragment(): AuthorFormFragment

}