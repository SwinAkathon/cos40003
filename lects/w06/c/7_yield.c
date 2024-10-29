int flag;

void init()
{
  flag = 0;
}

void lock()
{
  while (TestAndSet(&flag, 1) == 1)
    yield(); // give up the CPU
}

void unlock()
{
  flag = 0;
}