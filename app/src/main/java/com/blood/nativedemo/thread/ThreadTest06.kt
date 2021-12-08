package com.blood.nativedemo.thread

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/10/12 17:39
 * @Description:
 */
object ThreadTest06 {

    val lock = ReentrantLock()

    val condition = lock.newCondition()

    var value = 0

    @JvmStatic
    fun main(args: Array<String>) {
        println("thread test")

//        for (i in 1..10) {
//            thread {
//                try {
//                    lock.lock()
//                    value++
//                    println("value : $value")
//                } finally {
//                    lock.unlock()
//                }
//            }
//        }

        thread {
            try {
                lock.lock()
                println("${TimeUtil.curTime()} condition.await start")
                condition.await()
                println("${TimeUtil.curTime()} condition.await end")
            } finally {
                lock.unlock()
            }
        }

        thread {
            try {
                lock.lock()
                println("${TimeUtil.curTime()} condition.signal start")
                Thread.sleep(2000)
                condition.signal()
                println("${TimeUtil.curTime()} condition.signal end")
            } finally {
                lock.unlock()
            }
        }

    }

}