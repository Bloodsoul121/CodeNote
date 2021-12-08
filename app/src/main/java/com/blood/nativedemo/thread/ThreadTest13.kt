package com.blood.nativedemo.thread

import kotlin.concurrent.thread

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/10/12 17:39
 * @Description:
 */
object ThreadTest13 {

    private var isRunning = true

    @JvmStatic
    fun main(args: Array<String>) {
        println("thread test")

        println("thread start")
        val thread = thread {
            while (isRunning && !Thread.currentThread().isInterrupted) {
                try {
                    // do something
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                    println("InterruptedException")
                }
            }
            println("thread over")
        }

        Thread.sleep(2000)

        println("thread interrupt")
        thread.interrupt()

        Thread.sleep(2000)

        println("main over")
    }


}