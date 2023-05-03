package problem;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LearningLocks {
	
	public static void main(String[] args) throws InterruptedException {
		Dummy dummy=new Dummy();
	
		new Thread(() -> 
		{
			try {
				dummy.method1();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		)
.start();
		
		
		new Thread(() -> 
		{
			try {
				for(int i=0;i<10;i++) {
					Thread.sleep(1000);
					dummy.method2();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
				)
		.start();
	}
	
	

}


class Dummy{
	
	
	Lock lock=new ReentrantLock();
	Condition isWaiting=lock.newCondition();
	
	
	
	public void method1() throws InterruptedException {
		while(true) {
			
		lock.lock();
		
		System.out.println("Waiting now");
		isWaiting.await();
		System.out.println("Done waiting");
		
		lock.unlock();
		}
	}
	
	
	public void method2() throws InterruptedException {
		
		lock.lock();
		
		isWaiting.signalAll();
		
		lock.unlock();
		
		
	}
}
