package com.example.top_github.di.components

import android.app.Application
import com.example.top_github.di.modules.*
import com.example.top_github.ui.BaseApplication
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
    RepoListViewModelModule::class,
    NetworkCallModule::class])
interface ApplicationComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun injectApplication(application: Application): Builder

        fun build(): ApplicationComponent
    }

}