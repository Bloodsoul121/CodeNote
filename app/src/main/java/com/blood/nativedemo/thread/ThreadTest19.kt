package com.blood.nativedemo.thread

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/10/12 17:39
 * @Description:
 */
object ThreadTest19 {

    @JvmStatic
    fun main(args: Array<String>) {
        val hashCode1 = MyHashCode()
        val hashCode2 = MyHashCode()
        println("ThreadTest19 main: ${hashCode1 == hashCode2}")
    }
}


class MyHashCode {

    override fun equals(other: Any?): Boolean {
        println("MyHashCode equals: ")
        return super.equals(other)
    }

    override fun hashCode(): Int {
        println("MyHashCode hashCode: ")
        return super.hashCode()
    }

}