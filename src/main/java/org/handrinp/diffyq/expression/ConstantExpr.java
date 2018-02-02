package org.handrinp.diffyq.expression;

import org.handrinp.diffyq.Expression;

public class ConstantExpr extends Expression {
  private double value;

  public ConstantExpr(double value) {
    this.value = value;
  }

  public double getValue() {
    return value;
  }

  @Override
  public boolean isZero() {
    return Math.abs(value) < DELTA;
  }

  @Override
  public boolean isOne() {
    return Math.abs(value - 1) < DELTA;
  }

  @Override
  public double evaluate(double x) {
    return value;
  }

  @Override
  public Expression derivative() {
    return new ConstantExpr(0);
  }

  @Override
  public Expression reduce() {
    return isZero() ? Expression.ZERO : isOne() ? Expression.ONE : this;
  }

  @Override
  public String asString() {
    return Double.toString(value);
  }
}
