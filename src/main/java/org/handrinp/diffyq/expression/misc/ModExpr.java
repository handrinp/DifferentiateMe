package org.handrinp.diffyq.expression.misc;

import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.expression.ConstantExpr;

/**
 * generalized fraction, with both the numerator and denominator being expressions
 * 
 * @author handrinp
 */
public class ModExpr extends Expression {
  private Expression lhs;
  private Expression rhs;

  /**
   * construct a fraction with the given numerator and denominator
   * 
   * @param lhs first operand
   * @param rhs second operand
   */
  public ModExpr(Expression lhs, Expression rhs) {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  public ModExpr(Expression lhs, double r) {
    this(lhs, new ConstantExpr(r));
  }

  @Override
  public double evaluate(double x) {
    return lhs.evaluate(x) % rhs.evaluate(x);
  }

  @Override
  public Expression reduce() {
    return new ModExpr(lhs.reduce(), rhs.reduce());
  }

  @Override
  public String asString() {
    return "(" + lhs.asString() + " % " + rhs.asString() + ")";
  }

  @Override
  public int hash() {
    return 97 * lhs.hash() + rhs.hash();
  }
}
