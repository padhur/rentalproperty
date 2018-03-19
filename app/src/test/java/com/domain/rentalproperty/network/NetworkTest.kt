package com.domain.rentalproperty.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.junit.After
import org.junit.Before

import java.net.HttpURLConnection

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by padhu.r on 19/03/18.
 */
abstract class NetworkTest : BaseTest() {
    protected lateinit var mockWebServer: MockWebServer
    protected lateinit var serverUrl: String
    protected lateinit var gson: Gson
    protected lateinit var retrofit: Retrofit
    protected var loopback = "http://127.0.0.1:";

    protected fun enqueueResponseBody(body: String, StatusCode: Int) {
        mockWebServer.enqueue(MockResponse()
                .setResponseCode(StatusCode)
                .setBody(body))
    }

    @Before
    @Throws(Exception::class)
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        serverUrl =  loopback + mockWebServer.port + "/"

        gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

        retrofit = Retrofit.Builder()
                .baseUrl(serverUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Throws(Exception::class)
    fun setupResponse(resBody: String) {
        enqueueResponseBody(resBody, HttpURLConnection.HTTP_OK)

        //wait for network
        Thread.sleep(WAITING)
    }

    @Throws(Exception::class)
    fun setupResponse(resBody: String, statusCode: Int) {
        enqueueResponseBody(resBody, statusCode)

        //wait for network
        Thread.sleep(WAITING)
    }

    @After
    @Throws(Exception::class)
    fun teardown() {
        mockWebServer.shutdown()
    }

}
