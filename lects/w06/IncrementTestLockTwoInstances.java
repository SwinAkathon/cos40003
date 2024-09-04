import java.util.concurrent.locks.ReentrantLock;

public class IncrementTestLockTwoInstances implements Runnable{
	
	static int classData = 0;
	int	instanceData = 0;
	static ReentrantLock data_lock = new ReentrantLock();

	public void run() {
		int localData = 0;
		data_lock.lock();
		try{
			while (localData < 100000000) {
				localData++;
				instanceData++;
				classData++;
			}
		}
		finally {
			data_lock.unlock();
		}
		System.out.println("Thread "+ Thread.currentThread().getName() +
				" localData: " + localData +
				"\tinstanceData: " + instanceData +
				"\tclassData: " + classData);

	}

	
	public static void main(String[] args) {
		IncrementTestLockTwoInstances instance = new IncrementTestLockTwoInstances();
		IncrementTestLockTwoInstances instance2 = new IncrementTestLockTwoInstances();
		
		Thread t1 = new Thread(instance);
//		Thread t2 = new Thread(instance);
		Thread t3 = new Thread(instance2);

		t1.setName("t1");
//		t2.setName("t2");
		t3.setName("t3");
		
		t1.start();
//		t2.start();
		t3.start();
	}
}
