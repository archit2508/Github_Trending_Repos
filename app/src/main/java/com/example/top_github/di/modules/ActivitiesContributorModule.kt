package com.example.top_github.di.modules

import com.example.top_github.ui.activities.RepoListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesContributorModule{

    @ContributesAndroidInjector(modules = [RepoListViewModelModule::class])
    abstract fun contributeRepoListActivity(): RepoListActivity
}