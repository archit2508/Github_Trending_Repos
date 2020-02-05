package com.example.top_github.di.modules

import androidx.lifecycle.ViewModel
import com.example.top_github.di.viewModelKeys.ViewModelKey
import com.example.top_github.viewModels.RepoListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * This module binds repo list screen's view model to its respective key
 */
@Module
abstract class RepoListViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RepoListViewModel::class)
    abstract fun bindRepoListViewModel(viewModel: RepoListViewModel): ViewModel
}