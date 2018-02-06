package org.handrinp.diffyq.expression;

import org.handrinp.diffyq.Expression;

/**
 * a numerical constant
 * 
 * @author handrinp
 */
public class ConstantExpr extends Expression {
  private double value;

  /**
   * construct a constant
   * 
   * @param value constant value
   */
  public ConstantExpr(double value) {
    this.value = value;
  }

  /**
   * get the underlying double value for the constant
   * 
   * @return value
   */
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
