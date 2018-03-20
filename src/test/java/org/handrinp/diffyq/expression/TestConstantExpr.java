package org.handrinp.diffyq.expression;

import org.handrinp.diffyq.Constants;
import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.ExpressionTest;
import junit.framework.Assert;

public class TestConstantExpr extends ExpressionTest {
  @Override
  public void testFunction() {
    final ConstantExpr expr = new ConstantExpr(3.0);

    for (int i = 0; i < 100; ++i) {
      final double r = rand(-500.0, 500.0);
      Assert.assertEquals(3.0, expr.evaluate(r), Constants.DELTA);
    }

    for (int i = 0; i < 100; ++i) {
      final double r = rand(-500.0, 500.0);
      Assert.assertEquals(Math.PI, Constants.PI.evaluate(r), Constants.DELTA);
    }
  }

  @Override
  public void testSimplify() {
    final Expression expr = new ConstantExpr(3.0).reduce();

    for (int i = 0; i < 100; ++i) {
      final double r = rand(-500.0, 500.0);
      Assert.assertEquals(3.0, expr.evaluate(r), Constants.DELTA);
    }
  }
}
