package com.sgcom;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    public static void main(String[] args) {
        Pattern arrayPattern = Pattern.compile("(\\S+)\\[([0-9]+)\\]");
        Matcher m = null;
        boolean b = false;
        m = arrayPattern.matcher("savasdA12sadvasdv[3234]");
        if ((b = m.find())) {
            System.out.println(b + " " + m.group(1) + " " + m.group(2));
        } else {
            System.out.println(b);
        }
    }
}
