package weijianlin.concurrentProgrammingPractice.util;

import java.util.concurrent.CountDownLatch;

public class HighConcurrencyTesting {

	/**
	 * 高并发测试:创建threadNum个线程;执行任务task
	 * 闭锁:一个闭锁工作起来就像一个大门:直到闭锁打到终点状态之前,门一直都是关闭的,没有线程能够通过,在终点状态到来的时候,门开了,允许所有线程通过.闭锁是一次性使用对象:一旦进入最终状态,就不能被重置了
	 * 闭锁可以确保特定活动直到其他的活动完成后,才发生
	 * @param threadNum 线程数量
	 * @param task 任务
	 */
	public static void parallelTesk(int threadNum, Runnable task){
	    
	    // 1. CountDownLatch是一个灵活的闭锁实现.闭锁的状态包括一个计数器,初始化为一个正数,用来表示需要等待的事件数
	    final CountDownLatch startGate = new CountDownLatch(1);
	    final CountDownLatch endGate  = new CountDownLatch(threadNum);

	    // 2. 创建指定数量的线程 
	    for (int i = 0; i <threadNum; i++) {
	        Thread t = new Thread(() -> {
	            try {
	            	//await方法一直阻塞到计数器为零,此时所有的事件已经发生,达到终点状态,放行所有线程
	                startGate.await();
	                try {
	                    task.run();
	                } finally {
	                    endGate.countDown();
	                }
	            } catch (InterruptedException e) {

	            }
	        });

	        t.start();
	    }

	    // 3. 线程统一放行，并记录时间！
	    long start =  System.nanoTime();
	    //countDown方法对计数器做减操作,表示一个事件已经发生了
	    startGate.countDown();
	    try {
	        endGate.await();
	    } catch (InterruptedException e) {
	    }

	    long end = System.nanoTime();
	    System.out.println("cost times :" +(end - start));
	}
}
