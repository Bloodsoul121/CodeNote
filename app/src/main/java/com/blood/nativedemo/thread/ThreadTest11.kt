package com.blood.nativedemo.thread

import kotlin.concurrent.thread

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/10/13 16:31
 * @Description:
 */
object ThreadTest11 {

    class ThreadLocalExt : ThreadLocal<Int>() {

        override fun initialValue(): Int {
            return 1
        }

    }

    @JvmStatic
    fun main(args: Array<String>) {

        thread {
            val tl = ThreadLocal<Any>()
            println("${TimeUtil.curTime()} ${Thread.currentThread().name} ${tl.get()}")
        }

        thread {
            val tl = ThreadLocal<Any>()
            println("${TimeUtil.curTime()} ${Thread.currentThread().name} ${tl.get()}")
        }

        val tl = ThreadLocal<Any>()
        println("${TimeUtil.curTime()} ${Thread.currentThread().name} ${tl.get()}")

        MyThread().start()
        Thread(MyRunnable()).start()
    }


}

class MyThread : Thread() {

    override fun run() {
        super.run()
        // 执行任务
    }

}

class MyRunnable : Runnable {

    override fun run() {
        // 执行任务
    }

}