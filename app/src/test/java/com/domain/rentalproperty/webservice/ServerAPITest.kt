package com.domain.rentalproperty.webservice

import com.domain.rentalproperty.network.NetworkTest
import com.domain.rentalproperty.modelbean.RentalProperty
import com.domain.rentalproperty.network.WebService
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class ServerAPITest : NetworkTest() {
    lateinit var apiClient : ClientAPI
    var result : RentalProperty? = null

    @Before
    @Throws(Exception::class)
    fun setupService() {
        val apiService = retrofit
                .create(WebService::class.java)

        apiClient = ClientAPI(apiService)
    }

    @Test
    fun serverDown() {
        //invalid url
        val serverUrl = "https://197.123.0.212:8080/"
        var isGetError = false

        val apiService = Retrofit.Builder()
                .baseUrl(serverUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(WebService::class.java)

        apiClient = ClientAPI(apiService)
        apiClient.getRentalPropertyList()
                .observeOn(Schedulers.computation())
                .doAfterTerminate { lock.countDown() }
                .subscribe (
                        { assertFalse(true)},
                        { error ->
                            run {
                                assert(error is ConnectException)
                                isGetError = true
                            }
                        }
                )

        lock.await(WAITING, TimeUnit.MILLISECONDS)
        assert(isGetError)
    }

    @Test
    fun validateResponse() {
        setupResponse(fileToString("rentalpropertyList.json"))

        apiClient.getRentalPropertyList()
                .observeOn(Schedulers.computation())
                .doAfterTerminate { lock.countDown() }
                .subscribe (
                        {list -> result = list},
                        {assertFalse(true)}
                )

        lock.await(WAITING, TimeUnit.MILLISECONDS)

        assertEquals(result?.data?.size, 6)
        assertEquals(result?.data?.get(0)?.isPremium, false)
        assertEquals(result?.data?.get(1)?.location?.address_1, "71 York Street")
        assertEquals(result?.data?.get(2)?.isPremium, true)
        assertEquals(result?.data?.get(4)?.owner?.firstName, "Cindy")
        assertEquals(result?.data?.get(5)?.photo?.image?.url, "https://static.pexels.com/photos/164349/pexels-photo-164349.jpeg")
    }

    @Test
    fun forbiddenResponse() {
        result = RentalProperty()

        val errorBody = "forbidden"
        setupResponse(errorBody, HttpURLConnection.HTTP_FORBIDDEN)
        apiClient.getRentalPropertyList()
                .observeOn(Schedulers.computation())
                .doAfterTerminate { lock.countDown() }
                .subscribe(
                        { assertFalse(true)},
                        {
                            run {
                                assert(it is HttpException)
                                assertEquals((it as HttpException).code(), HttpURLConnection.HTTP_FORBIDDEN)
                                assertEquals(it.response().errorBody()?.string(), errorBody)
                                result = null
                            }
                        }
                )

        lock.await(WAITING, TimeUnit.MILLISECONDS)
        assertEquals(result, null)
    }
}
