package org.handrinp.diffyq.expression;

import org.handrinp.diffyq.Constants;
import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.ExpressionTest;
import junit.framework.Assert;

public class TestVariableExpr extends ExpressionTest {
  @Override
  public void testFunction() {
    final VariableExpr expr = new VariableExpr();

    for (int i = 0; i < 100; ++i) {
      final double r = rand(-500.0, 500.0);
      Assert.assertEquals(r, expr.evaluate(r), Constants.DELTA);
    }

    for (int i = 0; i < 100; ++i) {
      final double r = rand(-500.0, 500.0);
      Assert.assertEquals(r, Constants.X.evaluate(r), Constants.DELTA);
    }
  }

  @Override
  public void testSimplify() {
    final Expression expr = new VariableExpr().reduce();

    for (int i = 0; i < 100; ++i) {
      final double r = rand(-500.0, 500.0);
      Assert.assertEquals(r, expr.evaluate(r), Constants.DELTA);
    }
  }
}
