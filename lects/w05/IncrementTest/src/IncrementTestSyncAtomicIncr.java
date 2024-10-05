import java.util.concurrent.atomic.AtomicInteger;

/**
 * @overview 3rd version of synchronization: 
 *  - synchronize at the value increment level (not at the method level), 
 *    using AtomicInteger for both instance and class data.
 *  - Thread finishes last outputs correct value, thread finishes first outputs a smaller value
 */
public class IncrementTestSyncAtomicIncr implements Runnable{
	
	// static int classData = 0;
	// FIX: int	instanceData = 0;
	AtomicInteger instanceData = new AtomicInteger(0);
	AtomicInteger classData = new AtomicInteger(0);

	public void run() {
		int localData = 0;
		
    while (localData < 10000000) {
      localData++;
      // FIX: instanceData++;   
      // classData++;
      instanceData.incrementAndGet();
      classData.incrementAndGet();
    }

		System.out.println(Thread.currentThread().getName()+" => localData: " + localData + 
				"\tinstanceData: " + instanceData.get() + 
				"\tclassData: " + classData.get());
	}

	
	public static void main(String[] args) {
		
		Runnable instance = new IncrementTestSyncAtomicIncr();
		// Runnable instance2 = new IncrementTest();
		
		Thread t1 = new Thread(instance, "t1");
		Thread t2 = new Thread(instance, "t2");
		// Thread t2 = new Thread(instance2);		
		
		t1.start();
    
   /*  try {
      t1.join();
    } catch (InterruptedException ex) {} */

		t2.start();
	}
}
