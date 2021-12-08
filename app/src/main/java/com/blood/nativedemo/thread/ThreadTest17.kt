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
object ThreadTest17 {

    private val cyclicBarrier = CyclicBarrier(10) { println("threads ready") }
    private val countDownLatch = CountDownLatch(10)

    private val lock = TicketLock2()
    private var value = 0

    @JvmStatic
    fun main(args: Array<String>) {
        for (i in 1..10) {
            thread {
                cyclicBarrier.await()
                lock.lock()
                try {
                    value++
                } finally {
                    println("${Thread.currentThread().name} >> 取号 : ${lock.getTicketNum()} , value : $value")
                    lock.unlock()
                }
                countDownLatch.countDown()
            }
        }
        countDownLatch.await()
        println("value : $value")
    }
}

class TicketLock2 {

    /**
     * 服务号
     */
    private val serviceNum = AtomicInteger(1)

    /**
     * 排队号
     */
    private val ticketNum = AtomicInteger()

    /**
     * 新增一个ThreadLocal，用于存储每个线程的排队号
     */
    private val ticketNumHolder = ThreadLocal<Int>()

    /**
     * lock:获取锁，如果获取成功，返回当前线程的排队号，获取排队号用于释放锁.
     */
    fun lock() {
        val currentTicketNum = ticketNum.incrementAndGet()
        // 获取锁的时候，将当前线程的排队号保存起来
        ticketNumHolder.set(currentTicketNum)
        while (currentTicketNum != serviceNum.get()) {
            // Do nothing
        }
    }

    /**
     * unlock:释放锁，传入当前持有锁的线程的排队号
     */
    fun unlock() {
        // 释放锁，从ThreadLocal中获取当前线程的排队号
        val currentTickNum = ticketNumHolder.get()?.toInt() ?: 0
        serviceNum.compareAndSet(currentTickNum, currentTickNum + 1)
    }

    fun getTicketNum(): Int {
        return ticketNumHolder.get() ?: 0
    }

}