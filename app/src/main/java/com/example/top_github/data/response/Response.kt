package com.example.top_github.data.response

import com.example.top_github.data.model.TrendingRepos

/**
 * A generic api response class which will store
 * 1. data fetched when status is success
 * 2. exception when status is failed
 */
class Response{
    private var repos: List<TrendingRepos>? = null
    private var error: Throwable? = null

    constructor (repos: List<TrendingRepos>) {
        this.repos = repos
        this.error = null
    }

    constructor (error: Throwable) {
        this.error = error
        this.repos = null
    }

    fun getRepos(): List<TrendingRepos>? {
        return repos
    }

    fun setRepos(repos: List<TrendingRepos>) {
        this.repos = repos
    }

    fun getError(): Throwable? {
        return error
    }

    fun setError(error: Throwable) {
        this.error = error
    }
}