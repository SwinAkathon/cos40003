/* 
 It returns the old value pointed to by the old ptr, and simultaneously updates said value to new. 
 The key, of course, is that this sequence of operations is performed atomically. 
 The reason it is called “test and set” is that it enables you
to “test” the old value (which is what is returned) while simultaneously “setting” the memory location to a new value; 
as it turns out, this slightly more powerful instruction is enough to build a simple spin lock, as we now examine in Figure 28.3. Or better yet: figure it out first yourself!
 */

int TestAndSet(int *old_ptr, int new)
{
  int old = *old_ptr; // fetch old value at old_ptr
  *old_ptr = new;     // store ’new’ into old_ptr
  return old;         // return the old value
}

typedef struct __lock_t
{
  int flag;
} lock_t;

void init(lock_t *lock)
{
  // 0: lock is available, 1: lock is held
  lock->flag = 0;
}

void lock(lock_t *lock)
{
  while (TestAndSet(&lock->flag, 1) == 1)
    ; // spin-wait (do nothing)
}

void unlock(lock_t *lock)
{
  lock->flag = 0;
}
