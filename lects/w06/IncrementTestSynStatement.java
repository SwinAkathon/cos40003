//import java.util.concurrent.locks.ReentrantLock;

public class IncrementTestSynStatement implements Runnable{
	
	static int classData = 0;
	int	instanceData = 0;
	//private ReentrantLock lock = new ReentrantLock();
	Object o = new Object();
	
	public void run() {
		int localData = 0;
		
		synchronized(o) {
		while (localData < 100000000) {
			localData++;
			instanceData++;
			classData++;
			
		}
		
		System.out.println("localData: " + localData + 
				"\tinstanceData: " + instanceData + 
				"\tclassData: " + classData);
		}
	
	}

	
	public static void main(String[] args) {
		
		IncrementTestSynStatement instance = new IncrementTestSynStatement();
		IncrementTestSynStatement instance2 = new IncrementTestSynStatement();
		
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
