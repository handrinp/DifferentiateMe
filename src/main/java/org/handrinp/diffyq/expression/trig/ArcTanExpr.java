package org.handrinp.diffyq.expression.trig;

import org.handrinp.diffyq.Constants;
import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.expression.arithmetic.FractionExpr;
import org.handrinp.diffyq.expression.arithmetic.SumExpr;
import org.handrinp.diffyq.expression.exponential.PowerExpr;

/**
 * the trigonometric inverse tangent function
 * 
 * @author handrinp
 */
public class ArcTanExpr extends Expression {
  private Expression expr;

  /**
   * construct an inverse tangent expression
   * 
   * @param expr the input to the inverse tangent
   */
  public ArcTanExpr(Expression expr) {
    this.expr = expr;
  }

  @Override
  public double evaluate(double x) {
    return Math.atan(expr.evaluate(x));
  }

  @Override
  public Expression derivative() {
    return new FractionExpr(expr.derivative(),
        new SumExpr(Constants.ONE, PowerExpr.squared(Constants.X)));
  }

  @Override
  public Expression reduce() {
    return new ArcTanExpr(expr.reduce());
  }

  @Override
  public String asString() {
    return "tan(" + expr.asString() + ")";
  }

  @Override
  public int hash() {
    return 109 * expr.hash();
  }
}
