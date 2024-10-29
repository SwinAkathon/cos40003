This program exemplifies how deadlock can occur in a realistic sales transaction system where multiple threads manage shared resources. To avoid deadlocks in production code, we can use the following strategies: **consistent lock ordering**, **timeouts**, or **try-lock mechanisms**.

A program for handling **Product Sales Transactions** with simulated **deadlock**:

The four branches of this repo demonstrates deadlock and their solutions:
1. `w09deadlock`: the deadlock code
2. `w09fix_cicularwait`: resolves the circular wait problem
3. `w09fix_holdandwait`: resolves the hold-and-wait problem
4. `w09fix_nopreemption`: resolves the no-preemption problem
5. `w09fix_mutex`: presents an alternative design for mutex (especially suitable for large systems) .