package com.blood.nativedemo.thread

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.thread
import kotlin.concurrent.write

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/10/12 17:39
 * @Description:
 */
object ThreadTest07 {

    val lock = ReentrantReadWriteLock()
    val readLock = lock.readLock()
    val writeLock = lock.writeLock()

    @JvmStatic
    fun main(args: Array<String>) {

        thread {
            try {
                readLock.lock()
                println("${TimeUtil.curTime()} readLock start")
                Thread.sleep(1000)
                println("${TimeUtil.curTime()} readLock end")
            } finally {
                readLock.unlock()
            }
        }

        thread {
            try {
                writeLock.lock()
                println("${TimeUtil.curTime()} writeLock start")
                Thread.sleep(2000)
                println("${TimeUtil.curTime()} writeLock end")
            } finally {
                writeLock.unlock()
            }
        }

    }

}