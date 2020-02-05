package com.example.top_github.ui

import com.example.top_github.di.components.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BaseApplication : DaggerApplication() {

    /**
     * Injecting/Binding application class into application component
     */
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().injectApplication(this).build()
    }

}