package org.handrinp.diffyq.bool.logical;

import org.handrinp.diffyq.BooleanExpr;

/**
 * the logical not operator
 * 
 * @author handrinp
 */
public class NotExpr extends BooleanExpr {
  private BooleanExpr expr;

  /**
   * constructs a new not expression corresponding to !expr
   * 
   * @param expr
   */
  public NotExpr(BooleanExpr expr) {
    this.expr = expr;
  }

  /**
   * the underlying boolean expression
   * 
   * @return expr
   */
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
