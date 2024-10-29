import java.util.concurrent.locks.ReentrantLock;

public class Product {
    private final int id;
    private int quantity;
    public final ReentrantLock lock = new ReentrantLock();

    public Product(int id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    // Adjust product quantity safely using product.lock
    public void adjustQuantity(int amount) {
        lock.lock();
        try {
            quantity += amount;
        } finally {
            lock.unlock();
        }
    }

    // Reserve stock safely
    public boolean reserveStock(int amount) {
        lock.lock();
        try {
            if (quantity >= amount) {
                quantity -= amount;
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }
}