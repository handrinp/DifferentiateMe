package org.handrinp.diffyq.expression.trig;

import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.expression.arithmetic.FractionExpr;
import org.handrinp.diffyq.expression.arithmetic.NegateExpr;
import org.handrinp.diffyq.expression.arithmetic.SumExpr;
import org.handrinp.diffyq.expression.exponential.PowerExpr;

/**
 * the trigonometric cosine inverse function
 * 
 * @author handrinp
 */
public class CosInvExpr extends Expression {
  private Expression expr;

  /**
   * construct an inverse cosine expression
   * 
   * @param expr the input to the cosine inverse
   */
  public CosInvExpr(Expression expr) {
    this.expr = expr;
  }

  @Override
  public double evaluate(double x) {
    return Math.acos(expr.evaluate(x));
  }

  @Override
  public Expression derivative() {
    return new FractionExpr(new NegateExpr(expr.derivative()),
        PowerExpr.sqrt(new SumExpr(Expression.ONE, new NegateExpr(PowerExpr.squared(expr)))));
  }

  @Override
  public Expression reduce() {
    return new CosInvExpr(expr.reduce());
  }

  @Override
  public String asString() {
    return "cos(" + expr.asString() + ")";
  }
}
