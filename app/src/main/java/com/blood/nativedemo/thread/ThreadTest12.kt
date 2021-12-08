package com.blood.nativedemo.thread

import java.util.concurrent.Callable
import java.util.concurrent.FutureTask

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/10/12 17:39
 * @Description:
 */
object ThreadTest12 {

    @JvmStatic
    fun main(args: Array<String>) {
        println("thread test")

        val task = FutureTask(MyCallable())
        Thread(task).start()
        val result = task.get()
        println("result $result")
    }

    class MyCallable : Callable<String> {

        override fun call(): String {
            return "hello"
        }

    }

}