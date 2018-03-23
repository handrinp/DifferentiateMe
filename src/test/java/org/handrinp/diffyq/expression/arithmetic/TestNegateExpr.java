package org.handrinp.diffyq.expression.arithmetic;

import org.handrinp.diffyq.Constants;
import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.ExpressionTest;
import org.handrinp.diffyq.expression.ConstantExpr;
import junit.framework.Assert;

public class TestNegateExpr extends ExpressionTest {
  @Override
  public void testFunction() {
    final NegateExpr expr = new NegateExpr(Constants.X);

    for (int i = 0; i < 100; ++i) {
      final double r = rand(-500.0, 500.0);
      assertEq(-r, expr.evaluate(r));
    }
  }

  @Override
  public void testReduce() {
    final Expression expr = new NegateExpr(Constants.X).reduce();

    for (int i = 0; i < 100; ++i) {
      final double r = rand(-500.0, 500.0);
      assertEq(-r, expr.evaluate(r));
    }

    final Expression expr2 = new NegateExpr(new ConstantExpr(3.0)).reduce();

    Assert.assertTrue(expr2 instanceof ConstantExpr);

    for (int i = 0; i < 100; ++i) {
      final double r = rand(-500.0, 500.0);
      assertEq(-3.0, expr2.evaluate(r));
    }
  }

  @Override
  public void testDerivative() {
    final Expression expr = new NegateExpr(Constants.X).derivative();

    for (int i = 0; i < 100; ++i) {
      final double r = rand(-500.0, 500.0);
      assertEq(-1.0, expr.evaluate(r));
    }
  }
}
