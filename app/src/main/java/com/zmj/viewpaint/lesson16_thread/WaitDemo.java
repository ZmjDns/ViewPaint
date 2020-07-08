package com.zmj.viewpaint.lesson16_thread;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/8
 * Description :
 */
public class WaitDemo {

    private String sharedString;

    private synchronized void initString(){
        sharedString = "ssss";
        notifyAll();//唤醒所有在wait（）的线程
    }

    private synchronized void print(){
        //此处是一个循环只要满足条件线程一直在此处循环
        while (sharedString == null){
            try {
                //此处是wait（）而不是  Thread.wait()
                //因为方法上synchronized自动持有了此类的monitor与wait（）是一样的，
                // 以便于唤醒notifyAll()等待的线程
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(sharedString);
    }

    private void joinYield(){
        final Thread thread1 = new Thread(){
            @Override
            public void run() {
                //执行逻辑

            }
        };
        thread1.start();

        Thread thread2 = new Thread(){
            @Override
            public void run() {
                try {
                    thread1.join();         //等待thread1完成之后，在执行自己的线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Thread.yield();     //让出当前的时间片，给同一等级的线程，稍后再执行自己线程

                //执行逻辑

            }
        };

        thread2.start();
    }




}
