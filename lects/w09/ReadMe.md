This program exemplifies how deadlock can occur in a realistic sales transaction system where multiple threads manage shared resources. To avoid deadlocks in production code, we can use the following strategies: **consistent lock ordering**, **timeouts**, or **try-lock mechanisms**.

A program for handling **Product Sales Transactions** with simulated **deadlock**:

1. **Order Class** (`Order.java`):
   - Represents a sales order with fields for `id` and `status`. The default status is `"Pending"`, which updates to `"Fulfilled"` when the order is completed【265†source】.

2. **Product Class** (`Product.java`):
   - Represents a product with `id` and `quantity`. It includes a method to adjust the quantity, reflecting inventory changes as items are added or removed.

3. **ProductAdjustment Class** (`ProductAdjustment.java`):
   - This class simulates a process that adjusts the quantity of a product. It first locks the `Product` and then attempts to lock the `Order`. This sequential locking can lead to a deadlock if other threads lock the `Order` first and wait for the `Product`.

4. **OrderProcessor Class** (`OrderProcessor.java`):
   - Represents the process of fulfilling an order. It locks the `Order` first and then waits for the `Product` to update inventory during processing. This locking order conflicts with `ProductAdjustment` and leads to deadlock in concurrent scenarios.

5. **ProductSales Class** (`ProductSales.java`):
   - The `main` method in `ProductSales` initializes instances of `Product` and `Order`, then starts two threads: one for `ProductAdjustment` and another for `OrderProcessor`. The two threads create a deadlock situation, each waiting for the resource held by the other.
