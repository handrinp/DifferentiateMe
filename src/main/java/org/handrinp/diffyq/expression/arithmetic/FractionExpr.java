package org.handrinp.diffyq.expression.arithmetic;

import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.expression.ConstantExpr;
import org.handrinp.diffyq.expression.exponential.PowerExpr;

/**
 * generalized fraction, with both the numerator and denominator being expressions
 * 
 * @author handrinp
 */
public class FractionExpr extends Expression {
  private Expression u;
  private Expression v;

  /**
   * construct a fraction with the given numerator and denominator
   * 
   * @param numerator numerator
   * @param denominator denominator
   */
  public FractionExpr(Expression numerator, Expression denominator) {
    u = numerator;
    v = denominator;
  }

  /**
   * convenience method for inverse
   * 
   * @param expr expression to invert
   * @return 1/expr
   */
  public static FractionExpr inverse(Expression expr) {
    return new FractionExpr(Expression.ONE, expr);
  }

  /**
   * get the numerator of the fraction
   * 
   * @return u
   */
  public Expression getU() {
    return u;
  }

  /**
   * get the denominator of the fraction
   * 
   * @return v
   */
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
    final Expression newU = u.reduce();
    final Expression newV = v.reduce();

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

  @Override
  public int hash() {
    return 11 * u.hash() + v.hash();
  }
}
