package org.handrinp.diffyq.expression.arithmetic;

import org.handrinp.diffyq.Constants;
import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.ExpressionTest;
import org.handrinp.diffyq.expression.ConstantExpr;
import junit.framework.Assert;

public class TestProductExpr extends ExpressionTest {
  @Override
  public void testFunction() {
    final ProductExpr expr = new ProductExpr(new ConstantExpr(3.0), Constants.X);

    repeat(100, () -> {
      final double r = rand(-500.0, 500.0);
      assertEq(r + r + r, expr.evaluate(r));
    });

    final ProductExpr expr2 = new ProductExpr(Constants.X, Constants.X, Constants.X);

    repeat(100, () -> {
      final double r = rand(-500.0, 500.0);
      assertEq(r * r * r, expr2.evaluate(r));
    });
  }

  @Override
  public void testReduce() {
    final Expression expr = new ProductExpr(Constants.X, Constants.ONE, Constants.X).reduce();

    repeat(100, () -> {
      final double r = rand(-500.0, 500.0);
      assertEq(r * r, expr.evaluate(r));
    });

    final Expression expr2 =
        new ProductExpr(Constants.X, new FractionExpr(Constants.ONE, Constants.X)).reduce();

    Assert.assertTrue(expr2 instanceof ConstantExpr);

    repeat(100, () -> {
      final double r = rand(-500.0, 500.0);
      assertEq(1.0, expr2.evaluate(r));
    });
  }

  @Override
  public void testDerivative() {
    // TODO Auto-generated method stub

  }
}
