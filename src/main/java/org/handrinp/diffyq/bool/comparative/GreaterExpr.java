package org.handrinp.diffyq.bool.comparative;

import org.handrinp.diffyq.BooleanExpr;
import org.handrinp.diffyq.Expression;

/**
 * the comparative greater than operator
 * 
 * @author handrinp
 */
public class GreaterExpr extends BooleanExpr {
  private Expression lhs;
  private Expression rhs;

  /**
   * constructs a new greater than expression corresponding to lhs &gt; rhs
   * 
   * @param lhs first operand
   * @param rhs second operand
   */
  public GreaterExpr(Expression lhs, Expression rhs) {
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
    return lhs.evaluate(x) > rhs.evaluate(x);
  }

  @Override
  public BooleanExpr reduce() {
    return new GreaterExpr(lhs.reduce(), rhs.reduce());
  }

  @Override
  public String asString() {
    return "(" + lhs.asString() + " > " + rhs.asString() + ")";
  }

  @Override
  public int hash() {
    return 107 * lhs.hash() + rhs.hash();
  }
}
