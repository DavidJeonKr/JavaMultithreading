package com.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Starter {
    public static void main(String[] args) {

        BlockingQueue queue = new ArrayBlockingQueue(50);

        Thread p = new Thread(new Producer(queue));
        Thread c = new Thread(new Consumer(queue));

        p.start();
        c.start();


    }
}
