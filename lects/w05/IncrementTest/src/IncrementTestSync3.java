import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @overview Use object lock
 */
public class IncrementTestSync3 implements Runnable{
	
	static int classData = 0;
	int	instanceData = 0;
	
  // use fine-grained lock with synchronized
  private Object lock1 = new Object();

  // not synchronized
	public void run() {
		int localData = 0;

    synchronized(lock1) {
      while (localData < 10000000) {
        localData++;
        instanceData++;   
        classData++;
      }

      System.out.println(Thread.currentThread().getName()+" => localData: " + localData + 
          "\tinstanceData: " + instanceData + 
          "\tclassData: " + classData);
    }
	}

	
	public static void main(String[] args) {
		
		Runnable instance = new IncrementTestSync3();
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
