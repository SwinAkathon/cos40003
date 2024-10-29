public class ProductSales {

    public static void main(String[] args) {
        Product product = new Product(1, 100);
        Order order = new Order(101);

        // Create two threads simulating Product Adjustment and Order Processing
        Thread adjustProductThread = new Thread(new ProductAdjustment(product, order), "ProductAdjustment");
        Thread processOrderThread = new Thread(new OrderProcessor(order, product), "OrderProcessing");

        // Start both threads
        adjustProductThread.start();
        processOrderThread.start();
    }
}
