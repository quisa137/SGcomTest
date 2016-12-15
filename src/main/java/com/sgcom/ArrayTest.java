package com.sgcom;

public class ArrayTest {
    public static void main(String[] args) {
        String[] myArray = { "a", "b", "c" };
        long myL = 10000L;
        long[] myArray2 = { 0, 1000L, 10000L };
        System.out.println(myArray2.length);
        System.out.println(Boolean.toString(myArray instanceof String[]));
        System.out.println(Boolean.toString(myArray instanceof Object[]));
        System.out.println(Boolean.toString(myArray2.getClass().isArray()));
        System.out.println(Boolean.toString(myArray.getClass().isArray()));
    }
}
