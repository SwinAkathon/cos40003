
/**
 * @overview first attempt at synchronization. Method <tt>run</tt> is synchronized.
 */
public class IncrementTestSync1 implements Runnable{
	
	static int classData = 0;
	int	instanceData = 0;
	
  // use synchronized keyword
	public synchronized void run() {
		int localData = 0;
		
    while (localData < 10000000) {
      localData++;
      instanceData++;   
      classData++;
    }

		System.out.println("localData: " + localData + 
				"\tinstanceData: " + instanceData + 
				"\tclassData: " + classData);
	}

	
	public static void main(String[] args) {
		
		Runnable instance = new IncrementTestSync1();
		// Runnable instance2 = new IncrementTest();
		
		Thread t1 = new Thread(instance);
		Thread t2 = new Thread(instance);
		// Thread t2 = new Thread(instance2);		
		
		t1.start();
    
    /* try {
      t1.join();
    } catch (InterruptedException ex) {} */

		t2.start();
	}
}
