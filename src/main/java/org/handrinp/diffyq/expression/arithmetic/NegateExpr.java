package org.handrinp.diffyq.expression.arithmetic;

import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.expression.ConstantExpr;

public class NegateExpr extends Expression {
  private Expression expr;

  public NegateExpr(Expression expr) {
    this.expr = expr;
  }

  public Expression getExpr() {
    return expr;
  }

  @Override
  public boolean isZero() {
    return expr.isZero();
  }

  @Override
  public boolean isOne() {
    return expr instanceof ConstantExpr && Math.abs(((ConstantExpr) expr).getValue() + 1) < DELTA;
  }

  @Override
  public double evaluate(double x) {
    return -expr.evaluate(x);
  }

  @Override
  public Expression derivative() {
    return new NegateExpr(expr.derivative());
  }

  @Override
  public Expression reduce() {
    Expression newExpr = expr.reduce();

    if (newExpr instanceof NegateExpr) {
      newExpr = ((NegateExpr) newExpr).getExpr();
    }

    if (newExpr instanceof ConstantExpr) {
      return new ConstantExpr(-((ConstantExpr) newExpr).getValue());
    }

    return new NegateExpr(newExpr);
  }

  @Override
  public String asString() {
    return "-" + expr.asString();
  }
}
