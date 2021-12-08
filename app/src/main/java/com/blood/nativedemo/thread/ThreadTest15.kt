package com.blood.nativedemo.thread

import java.util.concurrent.CountDownLatch
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/10/12 17:39
 * @Description:
 */
object ThreadTest15 {

    private val ai = AtomicInteger(1)
    private val cyclicBarrier = CyclicBarrier(10) { println("thread ready") }
    private val countDownLatch = CountDownLatch(10)

    @JvmStatic
    fun main(args: Array<String>) {
        for (i in 1..10) {
            thread {
                cyclicBarrier.await()
                increment()
                countDownLatch.countDown()
            }
        }
        countDownLatch.await()
        println("ai : ${ai.get()}")
    }

    private fun increment() {
        do {
            val oldValue = ai.get()
            val newValue = oldValue + 1
            println("${Thread.currentThread().name} increment update $newValue")
        } while (!ai.compareAndSet(oldValue, newValue))
    }

}