public class ProductAdjustment implements Runnable {
    private final Product product;
    private final Order order;

    public ProductAdjustment(Product product, Order order) {
        this.product = product;
        this.order = order;
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
                System.out.println(Thread.currentThread().getName() + " locked Order and completed product adjustment.");
            }
        }
    }
}