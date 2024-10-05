import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @overview 2nd version of synchronization. Use ReentrantLock.
 */
public class IncrementTestSync2FixPrint implements Runnable{
	
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

      System.out.println(Thread.currentThread().getName()+" => localData: " + localData + 
          "\tinstanceData: " + instanceData + 
          "\tclassData: " + classData);
    } finally {
      // release lock
      lock.unlock();
    }
	}

	
	public static void main(String[] args) {
		
		Runnable instance = new IncrementTestSync2FixPrint();
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
