package com.blood.nativedemo.thread;

import java.util.Random;

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/10/13 10:43
 * @Description:
 */
public class RandomUtils {

    public static long nextLong(int min, int max) {
        Random random = new Random();
        return Math.abs(random.nextLong()) % (max - min) + min;
    }

}
