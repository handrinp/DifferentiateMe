package org.handrinp.diffyq.expression.trig;

import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.expression.arithmetic.FractionExpr;
import org.handrinp.diffyq.expression.arithmetic.NegateExpr;
import org.handrinp.diffyq.expression.arithmetic.SumExpr;
import org.handrinp.diffyq.expression.exponential.PowerExpr;

/**
 * the trigonometric sine inverse function
 * 
 * @author handrinp
 */
public class SinInvExpr extends Expression {
  private Expression expr;

  /**
   * construct a sine inverse expression
   * 
   * @param expr the input to the sine inverse
   */
  public SinInvExpr(Expression expr) {
    this.expr = expr;
  }

  @Override
  public double evaluate(double x) {
    return Math.asin(expr.evaluate(x));
  }

  @Override
  public Expression derivative() {
    return new FractionExpr(expr.derivative(),
        PowerExpr.sqrt(new SumExpr(Expression.ONE, new NegateExpr(PowerExpr.squared(expr)))));
  }

  @Override
  public Expression reduce() {
    return new SinInvExpr(expr.reduce());
  }

  @Override
  public String asString() {
    return "cos(" + expr.asString() + ")";
  }
}
