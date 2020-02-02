package com.example.top_github.di.modules

import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun getString(): String {
        return "Sample"
    }
}