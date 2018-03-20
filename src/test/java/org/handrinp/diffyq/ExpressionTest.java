package org.handrinp.diffyq;

import java.util.Random;
import org.junit.Test;

public abstract class ExpressionTest {
  final Random rand = new Random();

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
  public abstract void testSimplify();
}
