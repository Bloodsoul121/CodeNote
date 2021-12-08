package com.blood.nativedemo.thread

import java.util.concurrent.CountDownLatch
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/10/12 17:39
 * @Description:
 */
object ThreadTest16 {

    private val cyclicBarrier = CyclicBarrier(10) { println("threads ready") }
    private val countDownLatch = CountDownLatch(10)

    private val lock = TicketLock()
    private var value = 0

    @JvmStatic
    fun main(args: Array<String>) {
        for (i in 1..10) {
            thread {
                cyclicBarrier.await()
                val num = lock.lock()
                try {
                    value++
                } finally {
                    println("${Thread.currentThread().name} >> 取号 : $num , value : $value")
                    lock.unlock(num)
                }
                countDownLatch.countDown()
            }
        }
        countDownLatch.await()
        println("value : $value")
    }
}

class TicketLock {

    /**
     * 服务号
     */
    private val serviceNum = AtomicInteger(1)

    /**
     * 排队号
     */
    private val ticketNum = AtomicInteger()

    /**
     * lock:获取锁，如果获取成功，返回当前线程的排队号，获取排队号用于释放锁.
     */
    fun lock(): Int {
        val currentTicketNum = ticketNum.incrementAndGet()
        while (currentTicketNum != serviceNum.get()) {
            // Do nothing
        }
        return currentTicketNum
    }

    /**
     * unlock:释放锁，传入当前持有锁的线程的排队号
     */
    fun unlock(ticketNum: Int) {
        serviceNum.compareAndSet(ticketNum, ticketNum + 1)
    }

}