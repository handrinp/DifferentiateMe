package org.handrinp.diffyq;

public abstract class BooleanExpr {
  public abstract boolean evaluate(double x);

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

  public BooleanExpr reduce() {
    return this;
  }

  public abstract String asString();

  @Override
  public final String toString() {
    return asString();
  }
}
