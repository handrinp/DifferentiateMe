package org.handrinp.diffyq.expression.trig;

import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.expression.ConstantExpr;
import org.handrinp.diffyq.expression.arithmetic.FractionExpr;
import org.handrinp.diffyq.expression.exponential.PowerExpr;

public class TanExpr extends Expression {
  private Expression expr;

  public TanExpr(Expression expr) {
    this.expr = expr;
  }

  @Override
  public double evaluate(double x) {
    return Math.tan(expr.evaluate(x));
  }

  @Override
  public Expression derivative() {
    return new FractionExpr(expr.derivative(),
        new PowerExpr(new CosExpr(expr), new ConstantExpr(2)));
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
