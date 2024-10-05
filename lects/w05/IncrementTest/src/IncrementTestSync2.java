// import java.util.concurrent.locks.Lock;
// import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.*;

/**
 * @overview Use ReentrantLock.
 */
public class IncrementTestSync2 implements Runnable{
	
	static int classData = 0;
	int	instanceData = 0;
	
  // use explicit more (fine-grained) lock
  private Lock lock = new ReentrantLock(); 

  // not synchronized
	public void run() {
		int localData = 0;
		
    // acquire lock 
    lock.lock();

    try {
      while (localData < 10000000) {
        localData++;
        instanceData++;   
        classData++;
      }
      // System.out.println("localData: " + localData + 
			// 	"\tinstanceData: " + instanceData + 
			// 	"\tclassData: " + classData);
    } finally {
      // release lock
      lock.unlock();
    }

    // FIX: Race condition still occurs here at println!!!
    // Thread finishes first sometimes print the value > the expected value (10000000)
		System.out.println("localData: " + localData + 
				"\tinstanceData: " + instanceData + 
				"\tclassData: " + classData);
	}

	
	public static void main(String[] args) {
		
		Runnable instance = new IncrementTestSync2();
		// Runnable instance2 = new IncrementTest();
		
	  Thread t1 = new Thread(instance, "t1");
		Thread t2 = new Thread(instance, "t2");
		// Thread t2 = new Thread(instance2);		
		
		t1.start();
    
    /* try {
      t1.join();
    } catch (InterruptedException ex) {} */

		t2.start();
	}
}
