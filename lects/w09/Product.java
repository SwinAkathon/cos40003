import java.util.concurrent.atomic.AtomicInteger;

public class Product {
    private final int id;
    private final AtomicInteger quantity;  // Use AtomicInteger for non-mutex updates

    public Product(int id, int quantity) {
        this.id = id;
        this.quantity = new AtomicInteger(quantity);
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity.get();
    }

    // Atomically adjusts quantity without strict locking
    public boolean adjustQuantity(int amount) {
        int current;
        int newAmount;
        do {
            current = quantity.get();
            newAmount = current + amount;
            if (newAmount < 0) {
                System.out.println("Not enough stock for adjustment.");
                return false;
            }
        } while (!quantity.compareAndSet(current, newAmount));  // Retry if another thread modifies quantity
        return true;
    }
}
