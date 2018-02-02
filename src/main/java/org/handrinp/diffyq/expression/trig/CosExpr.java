package org.handrinp.diffyq.expression.trig;

import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.expression.arithmetic.NegateExpr;
import org.handrinp.diffyq.expression.arithmetic.ProductExpr;

public class CosExpr extends Expression {
  private Expression expr;

  public CosExpr(Expression expr) {
    this.expr = expr;
  }

  @Override
  public double evaluate(double x) {
    return Math.cos(expr.evaluate(x));
  }

  @Override
  public Expression derivative() {
    return new ProductExpr(new NegateExpr(new SinExpr(expr)), expr.derivative());
  }

  @Override
  public String asString() {
    return "cos(" + expr.asString() + ")";
  }
}
