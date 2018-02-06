package org.handrinp.diffyq.expression.trig;

import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.expression.arithmetic.FractionExpr;
import org.handrinp.diffyq.expression.arithmetic.SumExpr;
import org.handrinp.diffyq.expression.exponential.PowerExpr;

public class ArcTanExpr extends Expression {
  private Expression expr;

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
        new SumExpr(Expression.ONE, PowerExpr.squared(Expression.X)));
  }

  @Override
  public Expression reduce() {
    return new ArcTanExpr(expr.reduce());
  }

  @Override
  public String asString() {
    return "tan(" + expr.asString() + ")";
  }
}
