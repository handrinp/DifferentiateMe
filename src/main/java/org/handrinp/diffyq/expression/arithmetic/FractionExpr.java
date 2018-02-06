package org.handrinp.diffyq.expression.arithmetic;

import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.expression.ConstantExpr;
import org.handrinp.diffyq.expression.exponential.PowerExpr;

public class FractionExpr extends Expression {
  private Expression u;
  private Expression v;

  public FractionExpr(Expression numerator, Expression denominator) {
    u = numerator;
    v = denominator;
  }

  public Expression getU() {
    return u;
  }

  public Expression getV() {
    return v;
  }

  @Override
  public double evaluate(double x) {
    return u.evaluate(x) / v.evaluate(x);
  }

  @Override
  public Expression derivative() {
    return new FractionExpr(new SumExpr(new ProductExpr(u.derivative(), v),
        new NegateExpr(new ProductExpr(u, v.derivative()))), PowerExpr.squared(v));
  }

  @Override
  public Expression reduce() {
    Expression newU = u.reduce();
    Expression newV = v.reduce();

    if (newV.isOne()) {
      return newU;
    }

    // handle constant/constant
    if (newU instanceof ConstantExpr && newV instanceof ConstantExpr && !newV.isZero()) {
      return new ConstantExpr(((ConstantExpr) newU).getValue() / ((ConstantExpr) newV).getValue())
          .reduce();
    }

    // recursively handle fractions within fractions
    if (newU instanceof FractionExpr) {
      FractionExpr newUF = (FractionExpr) newU;

      if (newV instanceof FractionExpr) {
        FractionExpr newVF = (FractionExpr) newV;
        return new FractionExpr(new ProductExpr(newUF.u, newVF.v),
            new ProductExpr(newUF.v, newVF.u)).reduce();
      }
    }

    if (newV instanceof FractionExpr) {
      FractionExpr newVF = (FractionExpr) newV;
      return new FractionExpr(new ProductExpr(newU, newVF.u), newVF.v).reduce();
    }

    return new FractionExpr(newU, newV);
  }

  @Override
  public String asString() {
    return "(" + u.asString() + " / " + v.asString() + ")";
  }
}
