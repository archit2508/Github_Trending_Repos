package com.example.top_github.di.modules

import com.example.top_github.ui.activities.RepoDetailsActivity
import com.example.top_github.ui.activities.RepoListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module which injects activities into their respective component
 * Components will scan injected activities for field, method and constructor injections and fetch those dependencies from modules available
 */
@Module
abstract class ActivitiesContributorModule{

    @ContributesAndroidInjector(modules = [RepoListViewModelModule::class])
    abstract fun contributeRepoListActivity(): RepoListActivity

    @ContributesAndroidInjector
    abstract fun contributeRepoDetailsActivity(): RepoDetailsActivity
}