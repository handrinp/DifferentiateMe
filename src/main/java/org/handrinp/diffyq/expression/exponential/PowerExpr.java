package org.handrinp.diffyq.expression.exponential;

import org.handrinp.diffyq.Constants;
import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.expression.ConstantExpr;
import org.handrinp.diffyq.expression.arithmetic.NegateExpr;
import org.handrinp.diffyq.expression.arithmetic.ProductExpr;
import org.handrinp.diffyq.expression.arithmetic.SumExpr;

/**
 * generalized power expression, with both the base and exponent being expressions
 * 
 * @author handrinp
 */
public class PowerExpr extends Expression {
  private Expression u;
  private Expression v;

  /**
   * construct a power expression base^exponent
   * 
   * @param base base expression
   * @param exponent exponent expression
   */
  public PowerExpr(Expression base, Expression exponent) {
    this.u = base;
    this.v = exponent;
  }

  /**
   * construct a power expression with a constant exponent
   * 
   * @param base base expression
   * @param exponent exponent constant
   */
  public PowerExpr(Expression base, double exponent) {
    this.u = base;
    this.v = new ConstantExpr(exponent);
  }

  /**
   * construct a power expression with a constant base
   * 
   * @param base base constant
   * @param exponent exponent expression
   */
  public PowerExpr(double base, Expression exponent) {
    this.u = new ConstantExpr(base);
    this.v = exponent;
  }

  /**
   * convenience method for expressions squared
   * 
   * @param expr expression to square
   * @return expr^2
   */
  public static PowerExpr squared(Expression expr) {
    return new PowerExpr(expr, 2);
  }

  /**
   * convenience method for square roots of expressions
   * 
   * @param expr expression to square root
   * @return expr^(1/2) = sqrt(expr)
   */
  public static PowerExpr sqrt(Expression expr) {
    return new PowerExpr(expr, 0.5);
  }

  @Override
  public double evaluate(double x) {
    return Math.pow(u.evaluate(x), v.evaluate(x));
  }

  @Override
  public Expression derivative() {
    Expression du = u.derivative();
    Expression dv = v.derivative();
    Expression uToVMinus1 = new PowerExpr(u, new SumExpr(v, new NegateExpr(Constants.ONE)));
    return new SumExpr(new ProductExpr(v, du, uToVMinus1),
        new ProductExpr(this, dv, new LogExpr(u)));
  }

  @Override
  public Expression reduce() {
    if (v.isOne()) {
      return u;
    }

    if (v.isZero()) {
      return Constants.ONE;
    }

    return new PowerExpr(u.reduce(), v.reduce());
  }

  @Override
  public String asString() {
    return "(" + u.asString() + " ^ " + v.asString() + ")";
  }

  @Override
  public int hash() {
    return 47 * u.hash() + v.hash();
  }
}
