package com.queue;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{

    private BlockingQueue queue;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);

                Date date = new Date();
                String msg = "메시지" + date.toString();

                queue.add(msg);

                System.out.println("메시지를 생성합니다. [" + queue.size() + "]");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
