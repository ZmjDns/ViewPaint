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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/6/17
 * Description :
 */
public class ThreadTest {

    /**
     * volatile只对基本类型数据保持原子操作性，但是对变量自增不起作用（a++，a--等）
     * 对对象的赋值具有原子操作性，但是对对象的属性不具有原子保护性
     */
    private volatile int a = 0; //让变量a同时具有原子性和同步性    volatile类似于一个小型的synchronized

    /**
     * Atomic+基本类型，保证变量自增的同步性（a++，a--等）
     */
    private AtomicInteger atomicInteger ;

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
            //int count = 0;
            AtomicInteger count = new AtomicInteger(0); //保证线程安全
            @Override
            public Thread newThread(Runnable r) {
                //count++;
                return new Thread(r,"Thrad-" + count.incrementAndGet());
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

    private void callableTest(){
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                //doSomeThing
                return "done";
            }
        };
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(callable);

        try {
            String result = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读写锁
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    private int x = 0;
    private int y = 0;
    //改变数据的时候加写入锁，此时不允许读
    private void count(int newValue){
        writeLock.lock();
        try {
            x = newValue;
            y = newValue;
        }finally {
            writeLock.unlock();
        }
    }
    private void print(){
        readLock.lock();
        try{
            System.out.println("x = " + x + "," + "y = " + y );
        }finally {
            readLock.unlock();
        }
    }


}