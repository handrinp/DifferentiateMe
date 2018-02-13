package org.handrinp.diffyq.expression.exponential;

import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.expression.ConstantExpr;
import org.handrinp.diffyq.expression.arithmetic.FractionExpr;
import org.handrinp.diffyq.expression.arithmetic.ProductExpr;

/**
 * the logarithm function, with base e also known as a natural logarithm
 * 
 * @author handrinp
 */
public class LogExpr extends Expression {
  private Expression expr;

  /**
   * construct a logarithm expression (base e, or natural logarithm)
   * 
   * @param expr the input to the logarithm
   */
  public LogExpr(Expression expr) {
    this.expr = expr;
  }

  /**
   * create an equivalent expression to a logarithm with the specified base
   * 
   * @param base the base of the logarithm
   * @param expr the input to the logarithm
   * @return ln(expr) * 1/ln(base)
   */
  public static Expression logBase(double base, Expression expr) {
    return new ProductExpr(new ConstantExpr(1.0 / Math.log(base)), new LogExpr(expr));
  }

  @Override
  public Expression derivative() {
    return new FractionExpr(expr.derivative(), expr);
  }

  @Override
  public Expression reduce() {
    final Expression f = expr.reduce();

    if (f instanceof ConstantExpr) {
      return new ConstantExpr(Math.log(((ConstantExpr) f).getValue()));
    }

    return this;
  }

  @Override
  public double evaluate(double x) {
    return Math.log(expr.evaluate(x));
  }

  @Override
  public String asString() {
    return "ln(" + expr.asString() + ")";
  }

  @Override
  public int hash() {
    return 47 * expr.hash();
  }
}
