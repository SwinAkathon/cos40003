
class Order {

    private final int id;
    private String status;

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

    public void fulfillOrder() {
        status = "Fulfilled";
    }
}
