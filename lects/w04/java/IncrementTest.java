public class IncrementTest implements Runnable {
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

        System.out.println("localData: " + localData + 
                           "\ninstanceData: " + instanceData + 
                           "\nclassData: " + classData);
    }

    public static void main(String[] args) {
        IncrementTest instance = new IncrementTest();

        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);

        t1.start();
        t2.start();
    }
}
