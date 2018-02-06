package org.handrinp.diffyq;

/**
 * this is the root, abstract class on which every boolean (logical) expression in the program rests
 * 
 * the true power and beauty of logic is shown here
 * 
 * @author handrinp
 */
public abstract class BooleanExpr {
  public abstract boolean evaluate(double x);

  /**
   * true
   */
  public static final BooleanExpr TRUE = new BooleanExpr() {
    @Override
    public boolean evaluate(double x) {
      return true;
    }

    @Override
    public String asString() {
      return "true";
    }
  };

  /**
   * false
   */
  public static final BooleanExpr FALSE = new BooleanExpr() {
    @Override
    public boolean evaluate(double x) {
      return false;
    }

    @Override
    public String asString() {
      return "false";
    }
  };

  /**
   * simplify the boolean expression, if able
   * 
   * @return a new expression that is logically equivalent, but with complexity less than or equal
   *         to the original
   */
  public BooleanExpr reduce() {
    return this;
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
}
