package org.handrinp.diffyq.bool.comparative;

import org.handrinp.diffyq.BooleanExpr;
import org.handrinp.diffyq.Expression;

/**
 * the comparative greater than or equal to operator
 * 
 * @author handrinp
 */
public class GreaterEqualExpr extends BooleanExpr {
  private Expression lhs;
  private Expression rhs;

  /**
   * constructs a new greater than or equal to expression corresponding to lhs >= rhs
   * 
   * @param lhs
   * @param rhs
   */
  public GreaterEqualExpr(Expression lhs, Expression rhs) {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  @Override
  public boolean evaluate(double x) {
    final double lhsv = lhs.evaluate(x);
    final double rhsv = rhs.evaluate(x);
    return lhsv > rhsv || Math.abs(lhsv - rhsv) < Expression.DELTA;
  }

  @Override
  public BooleanExpr reduce() {
    return new GreaterEqualExpr(lhs.reduce(), rhs.reduce());
  }

  @Override
  public String asString() {
    return "(" + lhs.asString() + " >= " + rhs.asString() + ")";
  }
}
