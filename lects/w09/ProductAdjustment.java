public class ProductAdjustment implements Runnable {
    private final Product product;
    private final Order order;

    public ProductAdjustment(Product product, Order order) {
        this.product = product;
        this.order = order;
    }

    @Override
    public void run() {
        // Acquire all necessary locks together
        synchronized (product) {
            synchronized (order) {
                System.out.println(Thread.currentThread().getName() + " locked Product with ID " + product.getId());
                System.out.println(Thread.currentThread().getName() + " locked Order with ID " + order.getId());

                try {
                    // Simulate work with Product and Order resources
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + " completed product adjustment.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}