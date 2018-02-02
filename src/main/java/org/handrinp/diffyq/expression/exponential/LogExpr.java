package org.handrinp.diffyq.expression.exponential;

import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.expression.ConstantExpr;
import org.handrinp.diffyq.expression.arithmetic.FractionExpr;

public class LogExpr extends Expression {
  private Expression expr;

  public LogExpr(Expression expr) {
    this.expr = expr;
  }

  @Override
  public Expression derivative() {
    return new FractionExpr(expr.derivative(), expr);
  }

  @Override
  public Expression reduce() {
    if (expr instanceof ConstantExpr) {
      return new ConstantExpr(Math.log(((ConstantExpr) expr).getValue()));
    }

    return this;
  }

  @Override
  public double evaluate(double x) {
    return Math.log(expr.evaluate(x));
  }

  @Override
  public String asString() {
    return "ln(" + expr.asString() + ")";
  }

}
