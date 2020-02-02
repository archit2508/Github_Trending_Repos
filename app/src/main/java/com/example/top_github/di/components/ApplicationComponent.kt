package com.example.top_github.di.components

import android.app.Application
import com.example.top_github.BaseApplication
import com.example.top_github.di.modules.ActivitiesContributorModule
import com.example.top_github.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivitiesContributorModule::class,
    AppModule::class])
interface ApplicationComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun injectApplication(application: Application): Builder

        fun build(): ApplicationComponent
    }

}