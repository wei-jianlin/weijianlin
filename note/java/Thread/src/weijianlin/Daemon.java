package weijianlin;

import java.util.Date;

public class Daemon extends Thread{

	
	@Override
	public void run() {
		for(int i = 0; i < 1000; i++) {
			System.out.println(getName() + " " + i);
		}
	}

	/**守护进程
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Daemon daemon = new Daemon();
		daemon.setDaemon(true);
		daemon.start();
		for(int i = 0; i < 10; i++) {
			System.out.println(Thread.currentThread().getName() + "  " + i);
		}
	}

}
