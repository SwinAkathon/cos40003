public class Order {
    private final int id;
    private volatile String status;  // Use volatile for non-mutex updates

    public Order(int id) {
        this.id = id;
        this.status = "Pending";
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    // Atomically update order status without strict locking
    public boolean fulfillOrder() {
        if ("Pending".equals(status)) {
            status = "Fulfilled";
            return true;
        }
        return false;
    }

    public boolean cancelOrder() {
        if ("Pending".equals(status)) {
            status = "Canceled";
            return true;
        }
        return false;
    }
}