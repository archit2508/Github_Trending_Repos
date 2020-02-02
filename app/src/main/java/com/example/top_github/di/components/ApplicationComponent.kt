package com.example.top_github.di.components

import android.app.Application
import com.example.top_github.ui.BaseApplication
import com.example.top_github.di.modules.ActivitiesContributorModule
import com.example.top_github.di.modules.AppModule
import com.example.top_github.di.modules.RepoListViewModelModule
import com.example.top_github.di.modules.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivitiesContributorModule::class,
    AppModule::class,
    ViewModelFactoryModule::class,
    RepoListViewModelModule::class])
interface ApplicationComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun injectApplication(application: Application): Builder

        fun build(): ApplicationComponent
    }

}