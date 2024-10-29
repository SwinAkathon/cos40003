// define a simple lock as a struct
typedef struct __lock_t
{
  int flag;
} lock_t;

void init(lock_t *mutex)
{
  // 0 -> lock is available, 1 -> held
  mutex->flag = 0;
}

void lock(lock_t *mutex)
{
  while (mutex->flag == 1); // TEST the flag
                            // spin-wait (do nothing)
  mutex->flag = 1;         // now SET it!
}

void unlock(lock_t *mutex)
{
  mutex->flag = 0;
}