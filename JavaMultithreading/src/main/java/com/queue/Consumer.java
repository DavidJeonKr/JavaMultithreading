package com.queue;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{

    private BlockingQueue queue;

    public Consumer(BlockingQueue queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);

                String msg = (String) queue.take();

                System.out.println("메시지를 꺼냅니다. " + msg + "[" + queue.size() + "]");

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
    }
}
