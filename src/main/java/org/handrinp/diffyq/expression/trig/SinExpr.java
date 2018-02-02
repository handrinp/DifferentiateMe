package org.handrinp.diffyq.expression.trig;

import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.expression.arithmetic.ProductExpr;

public class SinExpr extends Expression {
  private Expression expr;

  public SinExpr(Expression expr) {
    this.expr = expr;
  }

  @Override
  public double evaluate(double x) {
    return Math.sin(expr.evaluate(x));
  }

  @Override
  public Expression derivative() {
    return new ProductExpr(new CosExpr(expr), expr.derivative());
  }

  @Override
  public String asString() {
    return "sin(" + expr.asString() + ")";
  }
}
