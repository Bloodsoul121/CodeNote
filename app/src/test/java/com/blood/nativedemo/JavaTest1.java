package com.blood.nativedemo;

import org.junit.Test;

public class JavaTest1 {

    @Test
    public void test() {
//        Colors color = Colors.BLUE;
//        System.out.println(color.getValue());

        String ip = "121.23.4.4";
        System.out.println(ip.replaceAll("\\.", ""));

    }

    private enum Colors {
        RED(1), BLUE(2), GREEN(3);

        private final int value;

        Colors(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
