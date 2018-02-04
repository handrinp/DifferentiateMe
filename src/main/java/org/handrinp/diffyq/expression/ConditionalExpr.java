package org.handrinp.diffyq.expression;

import org.handrinp.diffyq.BooleanExpr;
import org.handrinp.diffyq.Expression;

public class ConditionalExpr extends Expression {
  private Expression expr;
  private BooleanExpr condition;

  public ConditionalExpr(Expression expr, BooleanExpr condition) {
    this.expr = expr;
    this.condition = condition;
  }

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
