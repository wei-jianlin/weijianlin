package weijianlin.concurrentProgrammingPractice.one;

import java.util.concurrent.atomic.AtomicLong;

import weijianlin.concurrentProgrammingPractice.util.HighConcurrencyTesting;


public class UnsafeSequence {
	
	private static int value;
	
	/* 读一个volatile类型的变量,总会返回由某一线程所写入的最新值,并且不会加锁,也就不会引起执行线程的阻塞
	 * volatile变量只能保证可见性
	 * 只有满足了下面所有标准,才能使用volatile变量:
	 * 1.写入变量时并不依赖变量的当前值;或者能够确保只有单一的线程修改变量的值
	 * 2.变量不需要与其他的状态变量共同参与不变约束
	 * 3.而且,访问变量时,没有其他的原因需要加锁
	 */
	private static volatile int vValue;		
	
	private final static AtomicLong count = new AtomicLong(0);		//原子操作
	
	public static int getNext(){
		return value++;
	}
	
	public static int getNext3(){
		return vValue++;
	}
	
	/**
	 * 加锁可以保证可见性与原子性
	 */
	public static synchronized int getNext2(){
		return value++;
	}

	public static void main(String[] args) {
		HighConcurrencyTesting.parallelTesk(1000,() -> {
			//getNext();
			//getNext2();
			getNext3();		//每次执行的值都不一样
			//count.incrementAndGet();
        });
		
		System.out.println(value);
		System.out.println(vValue);		
		System.out.println(count.get());
	}
}
