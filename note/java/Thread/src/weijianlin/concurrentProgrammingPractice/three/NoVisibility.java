package weijianlin.concurrentProgrammingPractice.three;

import java.util.concurrent.CountDownLatch;

public class NoVisibility {

	private static boolean 	read;
	
	private static int number;
	
	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(1);
		new Thread(){
			@Override
			public void run() {
				try {
					latch.await();
					while(!read){
						Thread.yield();
					}
					System.out.println(number);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		new Thread(){
			@Override
			public void run() {
				try {
					latch.await();
					read = true;
					number = 42;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();		
		latch.countDown();
	}
}
