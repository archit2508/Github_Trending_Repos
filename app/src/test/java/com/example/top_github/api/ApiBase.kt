package com.example.top_github.api

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.BufferedSource
import okio.Okio
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets

/**
 * Acts as base class which mocks web server using MockWebServer for github api service testing
 */
@RunWith(JUnit4::class)
open class ApiBase<T> {

    private lateinit var mockWebServer: MockWebServer

    @Before
    @Throws(IOException::class)
    fun mockServer() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    @Throws(IOException::class)
    fun stopServer() {
        mockWebServer.shutdown()
    }

    @Throws(IOException::class)
    fun enqueueResponse(fileName: String) {
        val inputStream = ApiBase::class.java.classLoader?.getResourceAsStream(String.format("api-response/%s", fileName))
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        mockWebServer.enqueue(mockResponse.setBody((source as BufferedSource).readString(StandardCharsets.UTF_8)))
    }

    fun createService(clazz: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(clazz)
    }
}