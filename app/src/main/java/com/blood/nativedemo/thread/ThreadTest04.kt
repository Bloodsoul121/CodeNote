package com.blood.nativedemo.thread

import java.util.*
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/10/12 17:39
 * @Description:
 */
object ThreadTest04 {

    val countDownLatch = CountDownLatch(3)

    @JvmStatic
    fun main(args: Array<String>) {
        println("thread test")

        for (i in 0 until 3) {
            thread {
                println("${Date()} >> ${Thread.currentThread().name} start")
                Thread.sleep(RandomUtils.nextLong(1000, 3000))
                println("${Date()} >> ${Thread.currentThread().name} end")
                countDownLatch.countDown()
            }
        }

        countDownLatch.await()

        println("${Date()} >> ${Thread.currentThread().name}")
    }

}