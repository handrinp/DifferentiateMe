package org.handrinp.diffyq.bool;

import org.handrinp.diffyq.BooleanExpr;
import org.handrinp.diffyq.Expression;

public class LessExpr extends BooleanExpr {
  private Expression lhs;
  private Expression rhs;

  public LessExpr(Expression lhs, Expression rhs) {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  @Override
  public boolean evaluate(double x) {
    return lhs.evaluate(x) < rhs.evaluate(x);
  }

  @Override
  public BooleanExpr reduce() {
    return new LessExpr(lhs.reduce(), rhs.reduce());
  }

  @Override
  public String asString() {
    return "(" + lhs.asString() + " < " + rhs.asString() + ")";
  }
}
