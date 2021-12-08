package com.blood.nativedemo.thread

import java.io.PipedInputStream
import java.io.PipedOutputStream
import kotlin.concurrent.thread

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/10/13 16:31
 * @Description:
 */
object ThreadTest10 {

    @JvmStatic
    fun main(args: Array<String>) {

        val thread1 = thread {
            println("${TimeUtil.curTime()} thread1 start")
            Thread.sleep(2000)
            println("${TimeUtil.curTime()} thread1 end")
        }

        val thread2 = thread {
            println("${TimeUtil.curTime()} thread2 start")
            thread1.join()
            println("${TimeUtil.curTime()} thread2 end")
        }

    }
}