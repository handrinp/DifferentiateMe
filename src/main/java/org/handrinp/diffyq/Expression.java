package org.handrinp.diffyq;

import org.handrinp.diffyq.expression.ConstantExpr;
import org.handrinp.diffyq.expression.VariableExpr;

public abstract class Expression {
  public static final double DELTA = 0.0000001;

  public static final Expression ZERO = new ConstantExpr(0);
  public static final Expression ONE = new ConstantExpr(1);
  public static final Expression X = new VariableExpr();

  public abstract double evaluate(double x);

  public Expression derivative() {
    throw new UndefinedDerivativeException(asString());
  }

  public Expression reduce() {
    return this;
  }

  public boolean isZero() {
    return false;
  }

  public boolean isOne() {
    return false;
  }

  public abstract String asString();

  @Override
  public final String toString() {
    return asString();
  }
}
