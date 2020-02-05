package com.example.top_github.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.top_github.viewModels.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * Provides view model factory
 */
@Module
abstract class ViewModelFactoryModule{

    @Binds
    abstract fun getViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}