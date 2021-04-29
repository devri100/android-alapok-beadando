package hu.korsosrichard.demoapp.di

import hu.korsosrichard.demoapp.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityModule {

    @ContributesAndroidInjector (modules = [FragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity

}