package org.handrinp.diffyq.expression.exponential;

import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.expression.arithmetic.NegateExpr;
import org.handrinp.diffyq.expression.arithmetic.ProductExpr;
import org.handrinp.diffyq.expression.arithmetic.SumExpr;

public class PowerExpr extends Expression {
  private Expression u;
  private Expression v;

  public PowerExpr(Expression base, Expression exponent) {
    this.u = base;
    this.v = exponent;
  }

  @Override
  public double evaluate(double x) {
    return Math.pow(u.evaluate(x), v.evaluate(x));
  }

  @Override
  public Expression derivative() {
    Expression du = u.derivative();
    Expression dv = v.derivative();
    Expression uToVMinus1 = new PowerExpr(u, new SumExpr(v, new NegateExpr(Expression.ONE)));
    return new SumExpr(new ProductExpr(v, du, uToVMinus1),
        new ProductExpr(this, dv, new LogExpr(u)));
  }

  @Override
  public Expression reduce() {
    if (v.isOne()) {
      return u;
    }

    if (v.isZero()) {
      return Expression.ONE;
    }

    return new PowerExpr(u.reduce(), v.reduce());
  }

  @Override
  public String asString() {
    return "(" + u.asString() + " ^ " + v.asString() + ")";
  }
}
