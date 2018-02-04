package org.handrinp.diffyq.bool;

import org.handrinp.diffyq.BooleanExpr;

public class AndExpr extends BooleanExpr {
  private BooleanExpr lhs;
  private BooleanExpr rhs;

  public AndExpr(BooleanExpr lhs, BooleanExpr rhs) {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  @Override
  public boolean evaluate(double x) {
    return lhs.evaluate(x) && rhs.evaluate(x);
  }

  @Override
  public BooleanExpr reduce() {
    return new AndExpr(lhs.reduce(), rhs.reduce());
  }

  @Override
  public String asString() {
    return "(" + lhs.asString() + " && " + rhs.asString() + ")";
  }
}
