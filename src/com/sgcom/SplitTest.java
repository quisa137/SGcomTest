package com.sgcom;

public class SplitTest {
    public static void main(String[] args) {
        String source = "KST_FULL_DATE|[dd/MMM/yyyy:HH:mm:ss.SSS Z]";
        String[] split = source.split("\\|");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }
    }
}