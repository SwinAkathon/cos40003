public class Product {
    private final int id;
    private int quantity;

    public Product(int id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void adjustQuantity(int amount) {
        quantity += amount;
    }
}