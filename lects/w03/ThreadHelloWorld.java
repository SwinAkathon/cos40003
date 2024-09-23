
class ThreadHelloWorld {

  public static void main(String[] args) {
    System.out.println("Hello world COS40003");

    try {
      int time = 10000;
      System.out.printf("Sleeping %d (secs) %n", time);

      Thread.sleep(time);
    } catch (InterruptedException e) {}
  }
}