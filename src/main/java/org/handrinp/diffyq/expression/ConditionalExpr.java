package org.handrinp.diffyq.expression;

import java.util.function.DoublePredicate;
import org.handrinp.diffyq.Expression;

public class ConditionalExpr extends Expression {
  private Expression expr;
  private DoublePredicate condition;

  public ConditionalExpr(Expression expr, DoublePredicate condition) {
    this.expr = expr;
    this.condition = condition;
  }

  public boolean test(double x) {
    return condition.test(x);
  }

  @Override
  public double evaluate(double x) {
    return condition.test(x) ? expr.evaluate(x) : Double.NaN;
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
    // TODO
    return null;
  }
}
