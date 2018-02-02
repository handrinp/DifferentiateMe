package org.handrinp.diffyq.expression;

import org.handrinp.diffyq.Expression;

public class Variable extends Expression {
  public Variable() {}

  @Override
  public double evaluate(double x) {
    return x;
  }

  @Override
  public Expression derivative() {
    return new ConstantExpr(1.0);
  }

  @Override
  public String asString() {
    return "x";
  }
}
