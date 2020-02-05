package com.example.top_github.api

import com.example.top_github.data.service.TrendingReposService
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class GithubApiServiceTest : ApiBase<TrendingReposService>() {

    private lateinit var service: TrendingReposService

    @Before
    fun initService() {
        this.service = createService(TrendingReposService::class.java)
    }

    /**
     * tests api service for successful api call
     */
    @Test
    @Throws(IOException::class)
    fun fetchTrendingRepos() {
        enqueueResponse("test_repositories.json")
        val response = service.getTrendingRepos("java","weekly").blockingGet()
        Assert.assertNotNull(response)
        Assert.assertEquals(3, response.size)
    }
}