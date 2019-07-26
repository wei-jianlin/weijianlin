package weijianlin.concurrentProgrammingPractice.five;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class BoundedHashSet<T> {

	private final Set<T> set;
	
	//计数信号量:用来控制能够同时访问某特定资源的活动的数量,或者同时执行某一给定操作的数量
	//一个Semaphore管理一个有效的许可集;许可的初始量通过构造函数传递给Semaphore.活动能够获得许可(只要还有剩余许可),并在使用之后释放许可
	private final Semaphore sem;
	
	public BoundedHashSet(int bound){
		this.set = Collections.synchronizedSet(new HashSet<T>());
		this.sem = new Semaphore(bound);
	}
	
	public boolean add(T o) throws InterruptedException{
		//如果已经没有可用的许可了,那么acquire会一直阻塞,直到有可用的许可为止,或者被中断,或者操作超时;
		//acquire可以看做是占用一个许可
		sem.acquire();
		boolean wasAdded = false;
		try{
			wasAdded = set.add(o);
			return wasAdded;
		}finally{
			//没有添加成功,则返回占用的许可
			if(!wasAdded){
				//release 释放一个许可
				sem.release();
			}
		}
	}
	
	public boolean remove(T o){
		boolean wasRemoved = set.remove(o);
		if(wasRemoved) sem.release();
		return wasRemoved;
	}
	
	public static void main(String[] args) throws InterruptedException {
		BoundedHashSet<Object> boundedHashSet = new BoundedHashSet<Object>(1);
		System.out.println(boundedHashSet.add(1));
		System.out.println(boundedHashSet.add(2));
	}
}
