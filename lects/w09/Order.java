import java.util.concurrent.locks.ReentrantLock;

public class Order {
    private final int id;
    private String status;
    public final ReentrantLock lock = new ReentrantLock();

    public Order(int id) {
        this.id = id;
        this.status = "Pending";
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    // Fulfill order safely using order.lock
    public void fulfillOrder() {
        lock.lock();
        try {
            if (status.equals("Pending")) {
                status = "Fulfilled";
            }
        } finally {
            lock.unlock();
        }
    }

    // Cancel order safely
    public void cancelOrder() {
        lock.lock();
        try {
            if (status.equals("Pending")) {
                status = "Canceled";
            }
        } finally {
            lock.unlock();
        }
    }
}