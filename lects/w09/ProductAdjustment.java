public class ProductAdjustment implements Runnable {
    private final Product product;
    private final Order order;

    public ProductAdjustment(Product product, Order order) {
        this.product = product;
        this.order = order;
    }

    @Override
    public void run() {
        // Non-exclusive check and update for Product quantity and Order status
        if (product.adjustQuantity(-10)) {  // Try to reserve 10 units
            System.out.println(Thread.currentThread().getName() + " adjusted quantity for Product with ID " + product.getId());

            if (order.fulfillOrder()) {  // Non-mutex attempt to fulfill the order
                System.out.println(Thread.currentThread().getName() + " fulfilled Order with ID " + order.getId());
            } else {
                System.out.println(Thread.currentThread().getName() + " could not fulfill Order, rolling back product adjustment.");
                product.adjustQuantity(10);  // Rollback adjustment if order can't be fulfilled
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " could not adjust Product quantity due to insufficient stock.");
        }
    }
}
