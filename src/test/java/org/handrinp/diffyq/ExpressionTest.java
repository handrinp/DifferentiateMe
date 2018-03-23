package org.handrinp.diffyq;

import java.util.Random;
import org.junit.Test;
import junit.framework.Assert;

public abstract class ExpressionTest {
  final Random rand = new Random();

  public void repeat(int times, Runnable r) {
    for (int i = 0; i < times; ++i) {
      r.run();
    }
  }

  public void assertEq(double expected, double actual) {
    Assert.assertEquals(expected, actual, Constants.DELTA);
  }

  public double rand() {
    return rand.nextDouble();
  }

  public double rand(double range) {
    return rand() * range;
  }

  public double rand(double lo, double hi) {
    return rand(hi - lo) + lo;
  }

  @Test
  public abstract void testFunction();

  @Test
  public abstract void testReduce();

  @Test
  public abstract void testDerivative();
}
