
import java.util.concurrent.locks.*;

/* 
 * Same IncrementTest as w05 except that 2 instances of it are run by 2 threads. 
 * In this case, <tt>classData++</tt> is not incremented correctly by 2 threads.
 */
public class IncrementTest implements Runnable{
	
	static int classData = 0;
	int	instanceData = 0;
	
  // solution 2
  static ReentrantLock dataLock = new ReentrantLock();

  // Problem: still not enough to make classData thread-safe!!!
	public synchronized void run() {
		int localData = 0;
		
    // critical section
		while (localData < 10000000) {
			localData++;
			instanceData++;
      // classData++;

      // solution 1
      // synchronized (IncrementTest.class) {
      //  classData++;
      // }

      // solution 2
      dataLock.lock();
      try {
        classData++;  
      } finally {
        dataLock.unlock();
      }
		}

		System.out.println("localData: " + localData + 
				"\tinstanceData: " + instanceData + 
				"\tclassData: " + classData);
	}

	
	public static void main(String[] args) {
		
		Runnable instance = new IncrementTest();
		Runnable instance2 = new IncrementTest();
		
		Thread t1 = new Thread(instance);
		// Thread t2 = new Thread(instance);
		Thread t2 = new Thread(instance2);		
		
		t1.start();
		t2.start();
	}
}
