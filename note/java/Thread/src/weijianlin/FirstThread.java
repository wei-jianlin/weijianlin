package weijianlin;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author weijianlin
 * 实现callable 接口创建线程,实现线程返回值.FutureTask包装callable对象.
 */
public class FirstThread {

	public static void main(String[] args) {
		FutureTask<Integer> task = new FutureTask<Integer>((Callable<Integer>)() -> {
			int i = 0; 
			for(; i < 100; i++) {
				System.out.println(Thread.currentThread().getName() + 
						"的i的值:" + i);
			}
			return i;
		});
		
		for(int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + 
					"的循环变量i的值:" + i);
			if(i == 20) {
				new Thread(task).start();
			}
		}
		try {
			System.out.println("子线程的返回值:" + task.get());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
