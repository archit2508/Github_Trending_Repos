package com.example.top_github.di.modules

import com.example.top_github.activities.RepoListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesContributorModule{

    @ContributesAndroidInjector
    abstract fun contributeRepoListActivity(): RepoListActivity
}