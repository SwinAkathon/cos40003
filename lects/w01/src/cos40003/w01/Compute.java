import java.util.function.*;
import java.text.DecimalFormat;

class Compute {

  // arithmetic sum
  private static final BiFunction<Integer, Double, Double> computeAdd = (num, sum) -> num + sum;

  // square-root sum
  private static final BiFunction<Integer, Double, Double> computeSqrt = (num, sum) -> Math.sqrt(num) + sum;

  // geometric sum
  // Define the ratio (common ratio) for the geometric sum
  private static final int power = 3; 
  private static final BiFunction<Integer, Double, Double> computeGeo = (num, sum) -> sum + ((num != 0) ? 1/Math.pow(num, power) : ((double) num));

  public static final BiFunction<Integer, Double, Double> opt = 
    computeGeo
    // computeSqrt
    // computeAdd
    ;

  public static String format(double number) {
    // Format the total sum with thousand separators
    DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
    String formatted = decimalFormat.format(number);

    return formatted;
  }
}