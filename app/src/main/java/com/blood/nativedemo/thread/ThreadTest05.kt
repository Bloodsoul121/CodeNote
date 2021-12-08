package com.blood.nativedemo.thread

import java.util.*
import java.util.concurrent.CyclicBarrier
import kotlin.concurrent.thread

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/10/12 17:39
 * @Description:
 */
object ThreadTest05 {

    val cyclicBarrier = CyclicBarrier(3) {
        println("${Date()} >> ${Thread.currentThread().name} cyclicBarrier done")
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("thread test")

        for (i in 0 until 3) {
            thread {
                println("${Date()} >> ${Thread.currentThread().name} start")
                Thread.sleep(RandomUtils.nextLong(1000, 3000))
                cyclicBarrier.await()
                Thread.sleep(RandomUtils.nextLong(1000, 3000))
                println("${Date()} >> ${Thread.currentThread().name} end")
            }
        }

        for (i in 0 until 3) {
            thread {
                println("${Date()} >> ${Thread.currentThread().name} start")
                Thread.sleep(RandomUtils.nextLong(1000, 3000))
                cyclicBarrier.await()
                Thread.sleep(RandomUtils.nextLong(1000, 3000))
                println("${Date()} >> ${Thread.currentThread().name} end")
            }
        }

    }

}