
public class MsLunch {

    private long c1 = 0;
    private long c2 = 0;
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void inc1() {
        synchronized (lock1) {
            c1++;
        }
    }

    public void inc2() {
        synchronized (lock2) {
            c2++;
        }
    }

    public long getC1() {return c1;}

    public long getC2() {return c2;}

    public static void main(String[] args) {
      final MsLunch o1 = new MsLunch();

      final int COUNT = 10000;

      Thread t1 = new Thread(new Runnable() {
        @Override
        public void run() {
          int i = 0;
          while (i < COUNT) {
            o1.inc1();
            i++;
          }
          String name = Thread.currentThread().getName();
          System.out.println(name + "=> c1: " + o1.getC1());
          System.out.println(name + "=> c2: " + o1.getC2());
        }
      }, "t1");

      Thread t2 = new Thread(new Runnable() {
        @Override
        public void run() {
          int i = 0;
          while (i < COUNT) {
            o1.inc1();
            o1.inc2();
            i++;
          }

          String name = Thread.currentThread().getName();
          System.out.println(name + "=> c1: " + o1.getC1());
          System.out.println(name + "=> c2: " + o1.getC2());
        }
      }, "t2");

      t1.start();

      t2.start();
    }
}
