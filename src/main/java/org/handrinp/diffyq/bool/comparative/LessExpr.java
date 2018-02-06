package org.handrinp.diffyq.bool.comparative;

import org.handrinp.diffyq.BooleanExpr;
import org.handrinp.diffyq.Expression;

/**
 * the comparative less than operator
 * 
 * @author handrinp
 */
public class LessExpr extends BooleanExpr {
  private Expression lhs;
  private Expression rhs;

  /**
   * constructs a new less than expression corresponding to lhs < rhs
   * 
   * @param lhs
   * @param rhs
   */
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
