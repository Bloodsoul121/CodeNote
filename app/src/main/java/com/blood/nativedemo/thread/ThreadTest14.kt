package com.blood.nativedemo.thread

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/10/12 17:39
 * @Description:
 */
object ThreadTest14 {

    @JvmStatic
    fun main(args: Array<String>) {
        synchronized(this) {
            println("synchronized running")
        }
    }


}