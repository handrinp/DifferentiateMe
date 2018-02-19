package org.handrinp.diffyq.expression.misc;

import java.util.Random;
import org.handrinp.diffyq.Expression;

/**
 * random number generating function
 * 
 * @author handrinp
 */
public class RandomExpr extends Expression {
  private double lo;
  private double hi;
  private Random rand;

  /**
   * expression that generates a random number in [lo, hi]
   * 
   * @param lo lower bound
   * @param hi upper bound
   */
  public RandomExpr(double lo, double hi) {
    this.lo = lo;
    this.hi = hi;
    rand = new Random();
  }

  /**
   * expression that generates a random number in [0, hi]
   * 
   * @param hi upper bound
   */
  public RandomExpr(double hi) {
    this(0, hi);
  }

  /**
   * expression that generates a random number between [0, 1]
   */
  public RandomExpr() {
    this(0, 1);
  }

  @Override
  public double evaluate(double x) {
    return lo + rand.nextDouble() * (hi - lo);
  }

  @Override
  public String asString() {
    return "rand(" + lo + ", " + hi + ")";
  }

  @Override
  public int hash() {
    // TODO Auto-generated method stub
    return -1;
  }
}
