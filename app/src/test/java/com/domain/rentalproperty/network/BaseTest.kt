package com.domain.rentalproperty.network

import java.util.concurrent.CountDownLatch

/**
 * Created by padhu.r on 19/03/18.
 */

abstract class BaseTest {

    protected var lock = CountDownLatch(1)

    companion object {
        val WAITING = 2500L//milli seconds

        @Throws(Exception::class)
        fun fileToString(fileName: String): String {
            return this.javaClass.getResource("/$fileName").readText(Charsets.UTF_8)
        }
    }
}
