import java.util.concurrent.TimeUnit;

public class ProductAdjustment implements Runnable {
    private final Product product;
    private final Order order;

    public ProductAdjustment(Product product, Order order) {
        this.product = product;
        this.order = order;
    }

    @Override
    public void run() {
        try {
            // Attempt to reserve stock and adjust quantity if successful
            if (product.reserveStock(10)) {  // Try reserving 10 units
                System.out.println(Thread.currentThread().getName() + " reserved stock on Product with ID " + product.getId());

                // Adjust quantity in a lock-safe way
                product.adjustQuantity(-10);
                System.out.println(Thread.currentThread().getName() + " adjusted quantity on Product with ID " + product.getId());

                // Attempt to fulfill the order safely
                if (order.lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                    try {
                        order.fulfillOrder();  // Fulfills order in a lock-safe way
                        System.out.println(Thread.currentThread().getName() + " fulfilled Order with ID " + order.getId());
                    } finally {
                        order.lock.unlock();
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + " could not lock Order, releasing Product stock reservation.");
                    product.adjustQuantity(10);  // Roll back if unable to fulfill order
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " could not reserve stock on Product with ID " + product.getId());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}