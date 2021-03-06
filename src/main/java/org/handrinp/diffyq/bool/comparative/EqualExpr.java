package org.handrinp.diffyq.bool.comparative;

import org.handrinp.diffyq.BooleanExpr;
import org.handrinp.diffyq.Constants;
import org.handrinp.diffyq.Expression;

/**
 * the comparative equality operator
 * 
 * @author handrinp
 */
public class EqualExpr extends BooleanExpr {
  private Expression lhs;
  private Expression rhs;

  /**
   * construct a new equals expression corresponding to lhs == rhs
   * 
   * @param lhs first operand
   * @param rhs second operand
   */
  public EqualExpr(Expression lhs, Expression rhs) {
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
    return Math.abs(lhs.evaluate(x) - rhs.evaluate(x)) < Constants.DELTA;
  }

  @Override
  public BooleanExpr reduce() {
    return new EqualExpr(lhs.reduce(), rhs.reduce());
  }

  @Override
  public String asString() {
    return "(" + lhs.asString() + " == " + rhs.asString() + ")";
  }

  @Override
  public int hash() {
    return 101 * lhs.hash() + rhs.hash();
  }
}
