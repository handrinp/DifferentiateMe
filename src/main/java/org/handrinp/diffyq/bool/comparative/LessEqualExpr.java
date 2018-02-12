package org.handrinp.diffyq.bool.comparative;

import org.handrinp.diffyq.BooleanExpr;
import org.handrinp.diffyq.Expression;

/**
 * the comparative less than or equal to operator
 * 
 * @author handrinp
 */
public class LessEqualExpr extends BooleanExpr {
  private Expression lhs;
  private Expression rhs;

  /**
   * constructs a new less than or equal to expression corresponding the lhs &le; rhs
   * 
   * @param lhs first operand
   * @param rhs second operand
   */
  public LessEqualExpr(Expression lhs, Expression rhs) {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  /**
   * get the left operand
   * 
   * @return the LHS
   */
  public Expression getLHS() {
    return lhs;
  }

  /**
   * get the right operand
   * 
   * @return the RHS
   */
  public Expression getRHS() {
    return rhs;
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
