package org.handrinp.diffyq;

/**
 * this is the root, abstract class on which every numerical expression in the program rests
 * 
 * the true power and beauty of mathematics is shown here
 * 
 * @author handrinp
 */
public abstract class Expression {
  /**
   * evaluate the expression at the given value
   * 
   * @param x x
   * @return f(x)
   */
  public abstract double evaluate(double x);

  /**
   * calculate the derivative of this expression, if it exists
   * 
   * @return f'
   * @throws UndefinedDerivativeException if no derivative exists for f, or if it hasn't been
   *         implemented yet
   */
  public Expression derivative() {
    throw new UndefinedDerivativeException(asString());
  }

  /**
   * simplify the expression, if able
   * 
   * @return a new expression that is mathematically equivalent, but with complexity less than or
   *         equal to the original
   */
  public Expression reduce() {
    return this;
  }

  /**
   * whether the expression is zero, which is useful for sums, products, and reducing
   * 
   * @return true if the expression is zero
   */
  public boolean isZero() {
    return false;
  }

  /**
   * whether the expression is one, which is useful for products, fractions, and reducing
   * 
   * @return true if the expression is zero
   */
  public boolean isOne() {
    return false;
  }

  /**
   * the String representation of the expression
   * 
   * @return a human readable String
   */
  public abstract String asString();

  @Override
  public final String toString() {
    return asString();
  }

  public abstract int hash();

  @Override
  public final int hashCode() {
    return hash();
  }
}
