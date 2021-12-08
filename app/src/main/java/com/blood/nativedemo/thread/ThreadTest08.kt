package com.blood.nativedemo.thread

import kotlin.concurrent.thread

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/10/13 16:31
 * @Description:
 */
object ThreadTest08 {

    private val lock = Object()

    @JvmStatic
    fun main(args: Array<String>) {

        thread {
            synchronized(lock) {
                println("${TimeUtil.curTime()} wait start")
                lock.wait()
                println("${TimeUtil.curTime()} wait end")
            }
        }

        thread {
            synchronized(lock) {
                println("${TimeUtil.curTime()} notify start")
                Thread.sleep(2000)
                lock.notify()
                println("${TimeUtil.curTime()} notify end")
            }
        }

    }
}