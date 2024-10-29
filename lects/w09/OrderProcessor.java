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
        // Lock Product first, then Order to maintain consistent locking order
        synchronized (product) {
            System.out.println(Thread.currentThread().getName() + " locked Product with ID " + product.getId());

            try {
                Thread.sleep(100);  // Simulate work with Product resource
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " waiting to lock Order with ID " + order.getId());
            synchronized (order) {
                System.out.println(Thread.currentThread().getName() + " locked Order and completed order processing.");
            }
        }
    }
}
