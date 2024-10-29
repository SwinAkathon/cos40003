


# Product and Order locks
A realistic scenario for applying `product.lock` and `order.lock` would be in an **e-commerce** or **inventory management system**, where **Product** and **Order** represent a catalog item and a customer order, respectively. Below are examples of methods within `Product` and `Order` that would need synchronization, where applying `product.lock` and `order.lock` directly would ensure thread-safe, consistent access:

### Example Scenario:

1. **Product Class (`product.lock`)**:
   - **`adjustQuantity()`**: When an order is placed or canceled, this method adjusts the inventory count of a product. Locking `product.lock` ensures that multiple threads don’t modify the quantity simultaneously, preventing inventory inaccuracies.
   - **`reserveStock(int quantity)`**: Used when a customer adds an item to the cart, reserving the quantity in inventory temporarily until the order is processed. This prevents double booking of stock.

2. **Order Class (`order.lock`)**:
   - **`fulfillOrder()`**: Marks the order as "Fulfilled" and is responsible for finalizing the transaction. Locking `order.lock` ensures that only one thread updates the order status, avoiding issues with duplicate order fulfillment.
   - **`cancelOrder()`**: When a customer cancels an order, this method would roll back any reserved inventory for products in the order. Locking here ensures only one thread can cancel at a time, avoiding partial rollbacks or inconsistencies if the product quantity was already modified.

### Example Code Implementation:

```java
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
```

### Explanation:

1. **Product Locking**: Both `adjustQuantity()` and `reserveStock()` methods use `product.lock` to ensure only one thread modifies the product quantity at a time. This is essential in preventing overselling and maintaining accurate stock levels across concurrent transactions.
   
2. **Order Locking**: The `fulfillOrder()` and `cancelOrder()` methods use `order.lock` to prevent conflicts in updating the order status. Without these locks, two threads could theoretically attempt to fulfill and cancel the same order, leading to data inconsistencies.

### Why Use Locks This Way?

This design ensures that **shared resources** (`Product` and `Order` instances) are accessed in a controlled manner:
- **Consistency**: Prevents overselling of products by synchronizing inventory modifications.
- **Thread Safety**: Eliminates race conditions when fulfilling or canceling orders, ensuring that operations on orders and products are isolated from other threads.

By applying `product.lock` and `order.lock` directly to methods that modify state, we achieve thread-safe handling of transactions in the product sales scenario.