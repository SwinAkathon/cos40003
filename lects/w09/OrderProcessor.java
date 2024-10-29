// OrderProcessing class: Locks Order first and waits for Product
class OrderProcessor implements Runnable {

    private final Order order;
    private final Product product;

    public OrderProcessor(Order order, Product product) {
        this.order = order;
        this.product = product;
    }

    @Override
    public void run() {
        synchronized (order) {
            System.out.println(Thread.currentThread().getName() + " locked Order with ID " + order.getId());

            try {
                Thread.sleep(100);  // Simulate work with Order resource
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " waiting to lock Product with ID " + product.getId());
            synchronized (product) {
                System.out.println(Thread.currentThread().getName() + " locked Product and completed order processing.");
            }
        }
    }
}
