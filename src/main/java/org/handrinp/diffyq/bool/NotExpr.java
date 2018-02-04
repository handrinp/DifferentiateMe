package org.handrinp.diffyq.bool;

import org.handrinp.diffyq.BooleanExpr;

public class NotExpr extends BooleanExpr {
  private BooleanExpr expr;

  public NotExpr(BooleanExpr expr) {
    this.expr = expr;
  }

  public BooleanExpr getExpr() {
    return expr;
  }

  @Override
  public boolean evaluate(double x) {
    return !expr.evaluate(x);
  }

  @Override
  public BooleanExpr reduce() {
    if (expr instanceof NotExpr) {
      return ((NotExpr) expr).getExpr().reduce();
    }

    return new NotExpr(expr.reduce());
  }

  @Override
  public String asString() {
    return "!(" + expr.asString() + ")";
  }
}
