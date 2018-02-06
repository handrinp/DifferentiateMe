package org.handrinp.diffyq.bool.logical;

import org.handrinp.diffyq.BooleanExpr;

/**
 * the logical or operator
 * 
 * @author handrinp
 */
public class OrExpr extends BooleanExpr {
  private BooleanExpr lhs;
  private BooleanExpr rhs;

  /**
   * constructs a new or expression corresponding to lhs || rhs
   * 
   * @param lhs
   * @param rhs
   */
  public OrExpr(BooleanExpr lhs, BooleanExpr rhs) {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  @Override
  public boolean evaluate(double x) {
    return lhs.evaluate(x) || rhs.evaluate(x);
  }

  @Override
  public BooleanExpr reduce() {
    return new OrExpr(lhs.reduce(), rhs.reduce());
  }

  @Override
  public String asString() {
    return "(" + lhs.asString() + " || " + rhs.asString() + ")";
  }
}
