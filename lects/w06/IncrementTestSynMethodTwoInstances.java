public class IncrementTestSynMethodTwoInstances implements Runnable{
	
	static int classData = 0;
	int	instanceData = 0;
	
	public void run() {
		int localData = 0;
		
		//synchronized(IncrementTestSynMethodTwoInstances.class) {
		while (localData < 100000000) {
			localData++;
			instanceData++;
			//classData++;
			//not working
			synchronized (IncrementTestSynMethodTwoInstances.class) {
				classData++;
			}
		}
		System.out.println("Thread "+ Thread.currentThread().getName() + " localData: " + localData +
				"\tinstanceData: " + instanceData + 
				"\tclassData: " + classData);
		//}
	}

	
	public static void main(String[] args) {
		
		IncrementTestSynMethodTwoInstances instance = new IncrementTestSynMethodTwoInstances();
		IncrementTestSynMethodTwoInstances instance2 = new IncrementTestSynMethodTwoInstances();
		
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
