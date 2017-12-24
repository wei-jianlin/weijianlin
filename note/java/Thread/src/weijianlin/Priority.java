package weijianlin;

public class Priority extends Thread{
	
	public Priority() {}
	
	public Priority(String name) {
		setName(name);
	}
	
	@Override
	public void run() {
		for(int i = 0; i < 50; i++) {
			System.out.println(getName() + "优先级:" + getPriority() + 
					",循环变量i的值" + i);
		}
	}

	public static void main(String[] args) {
		Thread.currentThread().setPriority(NORM_PRIORITY);
		for(int i = 0; i < 30; i++) {
			if(i == 10) {
				Priority low = new Priority("低级");
				low.start();
				System.out.println("进程之初创建的优先级:" + low.getPriority());
				low.setPriority(MIN_PRIORITY);
			}
			if(i == 20) {
				Priority heigh = new Priority("高级");
				heigh.start();
				System.out.println("进程之初创建的优先级:" + heigh.getPriority());
				heigh.setPriority(MAX_PRIORITY);
			}
		}
	}

}
