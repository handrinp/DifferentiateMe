package org.handrinp.diffyq.expression;

import org.handrinp.diffyq.BooleanExpr;
import org.handrinp.diffyq.Expression;

/**
 * a numerical expression with a boolean expression as a condition
 * 
 * when evaluating a conditional expression, the value is either expr(x) if the condition holds, or
 * NaN if not
 * 
 * @author handrinp
 */
public class ConditionalExpr extends Expression {
  private Expression expr;
  private BooleanExpr condition;

  /**
   * construct a conditional expression with the given expression and condition
   * 
   * @param expr expr
   * @param condition condition
   */
  public ConditionalExpr(Expression expr, BooleanExpr condition) {
    this.expr = expr;
    this.condition = condition;
  }

  /**
   * check if the condition holds for the given x
   * 
   * @param x value to test
   * @return true if the condition holds
   */
  public boolean test(double x) {
    return condition.evaluate(x);
  }

  @Override
  public double evaluate(double x) {
    return test(x) ? expr.evaluate(x) : Double.NaN;
  }

  @Override
  public Expression derivative() {
    return new ConditionalExpr(expr.derivative(), condition);
  }

  @Override
  public Expression reduce() {
    return new ConditionalExpr(expr.reduce(), condition);
  }

  @Override
  public String asString() {
    return "{" + expr.toString() + ", if " + condition.asString() + "}";
  }
}
