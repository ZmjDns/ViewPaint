package com.zmj.viewpaint.lesson16_thread;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/6/17
 * Description :
 */
public class ThreadTest {

    public static void main(String[] args){

        System.out.println("Main run");
    }

    private static void threadPool(){
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingDeque<>(1000);
        Executor threadPool = new ThreadPoolExecutor(5,100,5, TimeUnit.SECONDS,blockingQueue);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        threadPool.execute(runnable);
    }

    private static void callable(){
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {

                try{
                    Thread.sleep(3000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return "Done";
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> future = executor.submit(callable);

        while (true){
            if (future.isDone()){
                try {
                    String result = future.get();//阻塞线程
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void threadFactory(){
        ThreadFactory threadFactory = new ThreadFactory() {
            int count = 0;
            @Override
            public Thread newThread(Runnable r) {
                count++;
                return new Thread(r,"Thrad-" + count);
            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "Running");
            }
        };

        Thread thread1 = threadFactory.newThread(runnable);


    }


}