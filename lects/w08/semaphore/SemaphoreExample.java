
import java.util.concurrent.Semaphore;

class SharedResource {

    private Semaphore semaphore;

    public SharedResource(int permits) {
        semaphore = new Semaphore(permits);  // Initialize the semaphore with 'permits' number of permits
    }

    public void accessResource(String threadName) {
        try {
            System.out.println(threadName + " is waiting to access the resource...");
            semaphore.acquire();  // Acquire a permit (decrements the semaphore)
            System.out.println(threadName + " has acquired access to the resource!");

            // Simulate resource access
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(threadName + " has released the resource.");
            semaphore.release();  // Release a permit (increments the semaphore)
        }
    }
}

class SemaphoreExample {

    public static void main(String[] args) {
        SharedResource resource = new SharedResource(3);  // Semaphore allows up to 3 threads simultaneously

        // Create 5 threads that will try to access the resource
        for (int i = 1; i <= 5; i++) {
            String threadName = "Thread " + i;
            new Thread(() -> resource.accessResource(threadName)).start();
        }
    }
}
