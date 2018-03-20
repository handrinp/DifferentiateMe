package org.handrinp.diffyq;

import org.handrinp.diffyq.expression.ConstantExpr;
import org.handrinp.diffyq.expression.VariableExpr;

public class Constants {
  /**
   * constant used in comparing
   */
  public static final double DELTA = 0.0000001;

  /**
   * additive identity for reals
   */
  public static final ConstantExpr ZERO = new ConstantExpr(0);

  /**
   * multiplicative identity for reals
   */
  public static final ConstantExpr ONE = new ConstantExpr(1);

  /**
   * x
   */
  public static final VariableExpr X = new VariableExpr();

  /**
   * ratio of circle's circumference to diameter
   */
  public static final ConstantExpr PI = new ConstantExpr(Math.PI);

  /**
   * Euler's constant
   */
  public static final ConstantExpr E = new ConstantExpr(Math.E);
}
