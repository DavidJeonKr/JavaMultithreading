package com.example;

import java.util.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                throw new RuntimeException("Intentinal Exception");

            }
        });
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("A critical error happend in thread " + t.getName()
                        + " the error is " + e.getMessage());
            }
        });
        thread.start();
    }
    //StackQueEx
    protected static class StackQueEx{
        public static void main(String[] args) {
            Stack st = new Stack();
            Queue q = new LinkedList();
            st.push("0");
            st.push("1");
            st.push("2");

            q.offer("0");
            q.offer("1");
            q.offer("2");

            System.out.println("=Stack = ");
            while (!st.empty()){
                System.out.println(st.pop());
            }

            System.out.println("= Queue - ");
            while (!q.isEmpty()) {
                System.out.println(q.poll());
            }
        }
    }
}

