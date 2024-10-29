
public class OrderProcessor implements Runnable {
    private final Order order;
    private final Product product;

    public OrderProcessor(Order order, Product product) {
        this.order = order;
        this.product = product;
    }

    @Override
    public void run() {
        try {
            // Attempt to fulfill the order first in a lock-safe way
            if (order.lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                try {
                    order.fulfillOrder();
                    System.out.println(Thread.currentThread().getName() + " fulfilled Order with ID " + order.getId());

                    // Adjust Product quantity if order is successfully fulfilled
                    if (product.reserveStock(10)) {  // Reserve stock in a lock-safe way
                        product.adjustQuantity(-10);  // Adjust the quantity safely
                        System.out.println(Thread.currentThread().getName() + " adjusted quantity on Product with ID " + product.getId());
                    } else {
                        System.out.println(Thread.currentThread().getName() + " could not reserve stock on Product, rolling back Order fulfillment.");
                        order.cancelOrder();  // Rollback IF unable to reserve stock
                    }
                } finally {
                    order.lock.unlock();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " could not lock Order for processing.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
