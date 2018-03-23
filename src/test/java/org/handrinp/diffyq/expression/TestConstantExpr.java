package org.handrinp.diffyq.expression;

import org.handrinp.diffyq.Constants;
import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.ExpressionTest;

public class TestConstantExpr extends ExpressionTest {
  @Override
  public void testFunction() {
    final ConstantExpr expr = new ConstantExpr(3.0);

    for (int i = 0; i < 100; ++i) {
      final double r = rand(-500.0, 500.0);
      assertEq(3.0, expr.evaluate(r));
    }

    for (int i = 0; i < 100; ++i) {
      final double r = rand(-500.0, 500.0);
      assertEq(Math.PI, Constants.PI.evaluate(r));
    }
  }

  @Override
  public void testReduce() {
    final Expression expr = new ConstantExpr(3.0).reduce();

    for (int i = 0; i < 100; ++i) {
      final double r = rand(-500.0, 500.0);
      assertEq(3.0, expr.evaluate(r));
    }
  }

  @Override
  public void testDerivative() {
    final Expression expr = new ConstantExpr(3.0).derivative();

    for (int i = 0; i < 100; ++i) {
      final double r = rand(-500.0, 500.0);
      assertEq(0.0, expr.evaluate(r));
    }
  }
}
