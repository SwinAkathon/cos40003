
public class IncrementTest implements Runnable{
	
	static int classData = 0;
	int	instanceData = 0;
	
	public void run() {
		int localData = 0;
		
    // critical section
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
		
		Runnable instance = new IncrementTest();
//		Runnable instance2 = new IncrementTest();
		
		Thread t1 = new Thread(instance);
		Thread t2 = new Thread(instance);
//		Thread t2 = new Thread(instance2);		
		
		t1.start();
		t2.start();
	}
}
