package com.example.sampleapp.di

import com.example.sampleapp.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MainActivityScope

@Module
abstract class ActivityBuilder {

    @MainActivityScope
    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun contributeMainActivityInjector(): MainActivity
}