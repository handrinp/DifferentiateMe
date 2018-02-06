package org.handrinp.diffyq.expression;

import org.handrinp.diffyq.Expression;

/**
 * represents an algebraic variable that can hold any numeric value
 * 
 * @author handrinp
 */
public class VariableExpr extends Expression {
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
