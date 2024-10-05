public class IncrementTest implements Runnable {
    static int objCount = 1;
    int objId;
    public IncrementTest() {
      objId = objCount++;
    }

    static int classData = 0;   // shared between all threads
    int instanceData = 0;       // shared between instances

    @Override
    public void run() {
        int localData = 0;      // not shared between threads

        while (localData < 10000000) {
            localData++;
            instanceData++;
            classData++;
        }

        System.out.println(this + " => localData: " + localData + 
                           "\ninstanceData: " + instanceData + 
                           "\nclassData: " + classData);
    }

    @Override
    public String toString() {
      return this.getClass().getSimpleName() + "-" + objId;
    }

    public static void main(String[] args) {
        IncrementTest instance = new IncrementTest();

        Thread t1 = new Thread(instance);
        Thread t2 = 
          new Thread(instance);
          // new Thread(new IncrementTest());

        t1.start();
        t2.start();
    }
}
