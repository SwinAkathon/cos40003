
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {

    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        Thread writer = new Thread(new Writer(sharedResource), "Writer");
        Thread reader1 = new Thread(new Reader(sharedResource), "Reader 1");
        Thread reader2 = new Thread(new Reader(sharedResource), "Reader 2");

        writer.start();
        reader1.start();
        reader2.start();
    }
}

class SharedResource {

    private final List<Integer> data = new ArrayList<>();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    public void write(int value) {
        writeLock.lock();
        try {
            data.add(value);
            System.out.println(Thread.currentThread().getName() + " added value: " + value);
        } finally {
            writeLock.unlock();
        }
    }

    public void read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " reading data: " + data);
        } finally {
            readLock.unlock();
        }
    }
}

class Writer implements Runnable {

    private final SharedResource sharedResource;

    public Writer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            sharedResource.write(i);
            try {
                Thread.sleep(1000); // Simulate time taken to write
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Reader implements Runnable {

    private final SharedResource sharedResource;

    public Reader(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            sharedResource.read();
            try {
                Thread.sleep(500); // Simulate time taken to read
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
