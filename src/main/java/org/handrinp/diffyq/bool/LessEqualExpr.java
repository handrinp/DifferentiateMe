package org.handrinp.diffyq.bool;

import org.handrinp.diffyq.BooleanExpr;
import org.handrinp.diffyq.Expression;

public class LessEqualExpr extends BooleanExpr {
  private Expression lhs;
  private Expression rhs;

  public LessEqualExpr(Expression lhs, Expression rhs) {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  @Override
  public boolean evaluate(double x) {
    final double lhsv = lhs.evaluate(x);
    final double rhsv = rhs.evaluate(x);
    return lhsv < rhsv || Math.abs(lhsv - rhsv) < Expression.DELTA;
  }

  @Override
  public BooleanExpr reduce() {
    return new LessEqualExpr(lhs.reduce(), rhs.reduce());
  }

  @Override
  public String asString() {
    return "(" + lhs.asString() + " <= " + rhs.asString() + ")";
  }
}
