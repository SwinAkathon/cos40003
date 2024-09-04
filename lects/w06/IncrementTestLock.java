import java.util.concurrent.locks.ReentrantLock;

public class IncrementTestLock implements Runnable{
	
	static int classData = 0;
	int	instanceData = 0;
	private ReentrantLock lock = new ReentrantLock();

	
	public void run() {
		int localData = 0;
		
		while (localData < 100000000) {
			localData++;
			
			lock.lock();
			try {
			instanceData++;
			classData++;
			}
			finally {
				lock.unlock();
			}
		}
		
		
		lock.lock();
		try {
		System.out.println("localData: " + localData + 
				"\tinstanceData: " + instanceData + 
				"\tclassData: " + classData);
		}
		finally {
			lock.unlock();
		}
	
	}

	
	public static void main(String[] args) {
		
		IncrementTestLock instance = new IncrementTestLock();
		IncrementTestLock instance2 = new IncrementTestLock();
		
		Thread t1 = new Thread(instance);
//		Thread t2 = new Thread(instance);
		Thread t2 = new Thread(instance2);		
		
		t1.start();

//		try {
//			Thread.sleep(2000);			
//		}catch(InterruptedException e) {
//		}
		
		t2.start();
	}
}
