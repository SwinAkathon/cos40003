int CompareAndSwap(int *ptr, int expected, int new)
{
  int original = *ptr;
  if (original == expected)
    *ptr = new;
  return original;
}

typedef struct __lock_t
{
  int flag;
} lock_t;

void lock(lock_t *lock)
{
  while (CompareAndSwap(&lock->flag, 0, 1) == 1)
    ; // spin
}
