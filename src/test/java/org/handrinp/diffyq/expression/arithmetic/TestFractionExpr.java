package org.handrinp.diffyq.expression.arithmetic;

import org.handrinp.diffyq.Constants;
import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.ExpressionTest;
import org.handrinp.diffyq.expression.ConstantExpr;
import org.handrinp.diffyq.expression.VariableExpr;
import junit.framework.Assert;

public class TestFractionExpr extends ExpressionTest {
  @Override
  public void testFunction() {
    final FractionExpr expr = new FractionExpr(Constants.ONE, Constants.X);

    repeat(100, () -> {
      final double r = rand(-500.0, 500.0);
      assertEq(1.0 / r, expr.evaluate(r));
    });

    final FractionExpr expr2 = new FractionExpr(Constants.X, Constants.PI);

    repeat(100, () -> {
      final double r = rand(-500.0, 500.0);
      assertEq(r / Math.PI, expr2.evaluate(r));
    });
  }

  @Override
  public void testReduce() {
    final Expression expr = new FractionExpr(Constants.PI, Constants.ONE).reduce();
    Assert.assertTrue(expr instanceof ConstantExpr);

    repeat(100, () -> {
      final double r = rand(-500.0, 500.0);
      assertEq(Math.PI, expr.evaluate(r));
    });

    final Expression expr2 = new FractionExpr(Constants.X, Constants.ONE).reduce();
    Assert.assertTrue(expr2 instanceof VariableExpr);

    repeat(100, () -> {
      final double r = rand(-500.0, 500.0);
      assertEq(r, expr2.evaluate(r));
    });

    final Expression expr3 =
        new FractionExpr(Constants.ONE, new FractionExpr(Constants.ONE, Constants.X)).reduce();
    Assert.assertTrue(expr3 instanceof VariableExpr);

    repeat(100, () -> {
      final double r = rand(-500.0, 500.0);
      assertEq(r, expr3.evaluate(r));
    });
  }

  @Override
  public void testDerivative() {
    final Expression expr = new FractionExpr(Constants.ONE, Constants.X).derivative();

    repeat(100, () -> {
      final double r = rand(-500.0, 500.0);
      assertEq(-1.0 / (r * r), expr.evaluate(r));
    });

    final Expression expr2 =
        new FractionExpr(Constants.X, new FractionExpr(Constants.ONE, Constants.X)).derivative();

    repeat(100, () -> {
      final double r = rand(-500.0, 500.0);
      assertEq(r + r, expr2.evaluate(r));
    });
  }
}
