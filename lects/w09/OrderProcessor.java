public class OrderProcessor implements Runnable {
    private final Order order;
    private final Product product;

    public OrderProcessor(Order order, Product product) {
        this.order = order;
        this.product = product;
    }

    @Override
    public void run() {
        // Non-exclusive attempt to fulfill the Order
        if (order.fulfillOrder()) {
            System.out.println(Thread.currentThread().getName() + " fulfilled Order with ID " + order.getId());

            if (product.adjustQuantity(-10)) {  // Adjust product quantity if order is fulfilled
                System.out.println(Thread.currentThread().getName() + " adjusted quantity for Product with ID " + product.getId());
            } else {
                System.out.println(Thread.currentThread().getName() + " could not adjust Product quantity, rolling back order.");
                order.cancelOrder();  // Rollback order if quantity adjustment fails
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " could not fulfill Order due to current status.");
        }
    }
}
