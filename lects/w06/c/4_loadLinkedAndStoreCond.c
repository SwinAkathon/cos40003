int LoadLinked(int *ptr)
{
  return *ptr;
}

int StoreConditional(int *ptr, int value)
{
  if (
      // no update to * ptr since LL to this addr
  )
  {
    *ptr = value;
    return 1; // success!
  }
  else
  {
    return 0; // failed to update
  }
}

typedef struct __lock_t
{
  int flag;
} lock_t;

void lock(lock_t *lock)
{
  /* original
  while (1)
  {
    while (LoadLinked(&lock->flag) == 1)
      ; // spin until it’s zero
    if (StoreConditional(&lock->flag, 1) == 1)
      return; // if set-to-1 was success: done
    // otherwise: try again
 
  }
  */
  // more concise form: David Capel
  while (LoadLinked(&lock->flag) ||
         !StoreConditional(&lock->flag, 1))
    ; // spin
}

void unlock(lock_t *lock)
{
  lock->flag = 0;
}
