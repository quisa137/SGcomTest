/*    */ package com.sgcom;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegexTest
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 13 */     Pattern arrayPattern = Pattern.compile("(\\S+)\\[([0-9]+)\\]");
/* 14 */     Matcher m = null;
/* 15 */     boolean b = false;
/* 16 */     m = arrayPattern.matcher("savasdA12sadvasdv[3234]");
/* 17 */     if ((b = m.find())) {
/* 18 */       System.out.println(b + " " + m.group(1) + " " + m.group(2));
/*    */     } else {
/* 20 */       System.out.println(b);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\SGcom\Documents\EclipsePrj\SGcomTest\bin\!\com\sgcom\RegexTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */