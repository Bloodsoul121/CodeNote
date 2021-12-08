package com.blood.nativedemo.thread;

import androidx.annotation.Nullable;

import java.util.HashSet;

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/10/24 21:42
 * @Description:
 */
public class ThreadTest20 {

    public static void main(String[] args) {
        MyHashCode hashCode1 = new MyHashCode();
        MyHashCode hashCode2 = new MyHashCode();
//        boolean equals = hashCode1 == hashCode2;
        boolean equals = hashCode1.equals(hashCode2);
        System.out.println("main equals: " + equals);

        HashSet<MyHashCode> set = new HashSet<>();
        set.add(hashCode1);
        set.add(hashCode2);
        for (MyHashCode hashcode : set) {
            System.out.println("main hashcode: " + hashcode.hashCode());
        }
    }

    public static class MyHashCode {

        @Override
        public boolean equals(@Nullable @org.jetbrains.annotations.Nullable Object obj) {
            System.out.println("MyHashCode equals: ");
//            return super.equals(obj);
            return true;
        }

        @Override
        public int hashCode() {
            System.out.println("MyHashCode hashCode: ");
//            return super.hashCode();
            return 1;
        }
    }

}


