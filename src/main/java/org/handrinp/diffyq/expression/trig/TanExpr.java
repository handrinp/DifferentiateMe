package org.handrinp.diffyq.expression.trig;

import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.expression.arithmetic.FractionExpr;
import org.handrinp.diffyq.expression.exponential.PowerExpr;

/**
 * the trigonometric tangent function
 * 
 * @author handrinp
 */
public class TanExpr extends Expression {
  private Expression expr;

  /**
   * construct a tangent expression
   * 
   * @param expr the input to the tangent
   */
  public TanExpr(Expression expr) {
    this.expr = expr;
  }

  @Override
  public double evaluate(double x) {
    return Math.tan(expr.evaluate(x));
  }

  @Override
  public Expression derivative() {
    return new FractionExpr(expr.derivative(), PowerExpr.squared(new CosExpr(expr)));
  }

  @Override
  public Expression reduce() {
    return new TanExpr(expr.reduce());
  }

  @Override
  public String asString() {
    return "tan(" + expr.asString() + ")";
  }
}
