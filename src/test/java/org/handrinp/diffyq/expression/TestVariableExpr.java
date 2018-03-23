package org.handrinp.diffyq.expression;

import org.handrinp.diffyq.Constants;
import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.ExpressionTest;

public class TestVariableExpr extends ExpressionTest {
  @Override
  public void testFunction() {
    final VariableExpr expr = new VariableExpr();

    for (int i = 0; i < 100; ++i) {
      final double r = rand(-500.0, 500.0);
      assertEq(r, expr.evaluate(r));
    }

    for (int i = 0; i < 100; ++i) {
      final double r = rand(-500.0, 500.0);
      assertEq(r, Constants.X.evaluate(r));
    }
  }

  @Override
  public void testReduce() {
    final Expression expr = new VariableExpr().reduce();

    for (int i = 0; i < 100; ++i) {
      final double r = rand(-500.0, 500.0);
      assertEq(r, expr.evaluate(r));
    }
  }

  @Override
  public void testDerivative() {
    final Expression expr = new VariableExpr().derivative();

    for (int i = 0; i < 100; ++i) {
      final double r = rand(-500.0, 500.0);
      assertEq(1.0, expr.evaluate(r));
    }
  }
}
