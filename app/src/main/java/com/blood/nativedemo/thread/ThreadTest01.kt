package com.blood.nativedemo.thread

import java.util.concurrent.Semaphore
import kotlin.concurrent.thread

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/10/12 17:39
 * @Description:
 */
object ThreadTest01 {

    private val semaphore = Semaphore(2, true)

    @JvmStatic
    fun main(args: Array<String>) {
        println("thread test")

//        val thread1 = thread {
//            Thread.sleep(2000)
//            semaphore.acquire()
//            println(Thread.currentThread().name)
//            semaphore.release(1)
//        }
//
//        val thread2 = thread {
//            Thread.sleep(1000)
//            semaphore.acquire()
//            println(Thread.currentThread().name)
//            semaphore.release(1)
//        }

//        thread1.start()
//        thread2.start()


        for (i in 1..5) {
            thread {
                semaphore.acquire()
                println(Thread.currentThread().name)
                Thread.sleep(1000)
                semaphore.release(1)
            }
        }

        Thread.sleep(3000)
    }

}