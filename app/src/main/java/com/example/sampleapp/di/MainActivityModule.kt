package com.example.sampleapp.di

import com.example.sampleapp.viewmodels.MainActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @MainActivityScope
    @Provides
    fun provideViewModel(): MainActivityViewModel = MainActivityViewModel()
}