
## Vector deadlock
Folder: `./vector`.

This example highlights the risks of lock acquisition order when working with synchronized collections or methods that implicitly lock on multiple resources.

1. **Deadlock Scenario**:
   - Two `Vector` instances, `v1` and `v2`, are involved.
   - When `v1.addAll(v2)` is called, `addAll()` acquires locks on both `v1` and `v2` to add the contents of `v2` to `v1`.
   - The `addAll()` method acquires these locks in a specific order, which in this example is assumed to be `v1` first, then `v2`.

2. **Potential for Deadlock**:
   - If another thread simultaneously calls `v2.addAll(v1)`, it will try to lock `v2` first and then `v1`.
   - This creates a circular wait: the first thread is holding the lock on `v1` and waiting for `v2`, while the second thread holds the lock on `v2` and waits for `v1`.
   - This circular waiting condition can lead to a **deadlock**, as neither thread can proceed.

3. **Hidden Nature of Deadlock**:
   - This type of deadlock is subtle and **hidden from the calling application**, as the `addAll()` method does not explicitly show the locking mechanism to the user. This makes it difficult to foresee or prevent deadlock from outside the method.

## ProductSales with deadlock
This program exemplifies how deadlock can occur in a realistic sales transaction system where multiple threads manage shared resources. To avoid deadlocks in production code, we can use the following strategies: **consistent lock ordering**, **timeouts**, or **try-lock mechanisms**.

A program for handling **Product Sales Transactions** with simulated **deadlock**:

The four branches of this repo demonstrates deadlock and their solutions:
1. `w09deadlock`: the deadlock code
2. `w09fix_cicularwait`: resolves the circular wait problem
3. `w09fix_holdandwait`: resolves the hold-and-wait problem
4. `w09fix_nopreemption`: resolves the no-preemption problem
5. `w09fix_mutex`: presents an alternative design for mutex (especially suitable for large systems) .