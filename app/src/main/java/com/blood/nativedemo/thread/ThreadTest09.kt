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
object ThreadTest09 {

    private val pis = PipedInputStream()
    private val pos = PipedOutputStream()

    @JvmStatic
    fun main(args: Array<String>) {

        pis.connect(pos) // 关键

        thread {
            println("${TimeUtil.curTime()} write start")
            pos.write("hello".toByteArray())
            println("${TimeUtil.curTime()} write end")
        }

        thread {
            println("${TimeUtil.curTime()} read start")
            val buffer = ByteArray(1024)
            pis.read(buffer)
            println("${TimeUtil.curTime()} read : ${String(buffer)}")
            println("${TimeUtil.curTime()} read end")
        }

    }
}