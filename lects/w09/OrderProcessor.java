public class OrderProcessor implements Runnable {
    private final Order order;
    private final Product product;

    public OrderProcessor(Order order, Product product) {
        this.order = order;
        this.product = product;
    }

    @Override
    public void run() {
        // Acquire all necessary locks together
        synchronized (order) {
          synchronized (product) {
                System.out.println(Thread.currentThread().getName() + " locked Product with ID " + product.getId());
                System.out.println(Thread.currentThread().getName() + " locked Order with ID " + order.getId());

                try {
                    // Simulate work with Product and Order resources
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + " completed order processing.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}