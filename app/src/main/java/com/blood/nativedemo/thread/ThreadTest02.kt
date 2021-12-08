package com.blood.nativedemo.thread

import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Semaphore

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/10/12 17:39
 * @Description:
 */
object ThreadTest02 {

    // 排队总人数（请求总数）
    private val clientTotal = 10

    // 可同时受理业务的窗口数量（同时并发执行的线程数）
    private val threadTotal: Int = 2

    @JvmStatic
    fun main(args: Array<String>) {
        println("thread test")

        val executorService: ExecutorService = Executors.newCachedThreadPool()
        val semaphore = Semaphore(threadTotal, true)
        val countDownLatch = CountDownLatch(clientTotal)
        for (i in 0 until clientTotal) {
            executorService.execute {
                try {
                    semaphore.acquire(1)
                    resolve(i)
                    semaphore.release(1)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                countDownLatch.countDown()
            }
        }
        countDownLatch.await()
        executorService.shutdown()
    }

    @Throws(InterruptedException::class)
    private fun resolve(i: Int) {
        println("${Date()} 服务号{$i}，受理业务中。。。")
        Thread.sleep(2000)
    }

}