package org.handrinp.diffyq.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.handrinp.diffyq.Expression;

/**
 * an expression with one or more conditional expression pieces
 * 
 * @author handrinp
 */
public class PiecewiseExpr extends Expression {
  private List<ConditionalExpr> exprs;

  /**
   * construct a piecewise expression with the given pieces
   * 
   * @param exprs
   */
  public PiecewiseExpr(List<ConditionalExpr> exprs) {
    this.exprs = new ArrayList<>();
    this.exprs.addAll(exprs);
  }

  /**
   * construct a piecewise expression with the given pieces
   * 
   * @param exprs
   */
  public PiecewiseExpr(ConditionalExpr... exprs) {
    this(Arrays.asList(exprs));
  }

  @Override
  public double evaluate(double x) {
    for (int i = 0; i < exprs.size(); ++i) {
      if (exprs.get(i).test(x)) {
        return exprs.get(i).evaluate(x);
      }
    }

    return Double.NaN;
  }

  @Override
  public Expression derivative() {
    return new PiecewiseExpr(exprs.stream().map(Expression::derivative)
        .map(f -> (ConditionalExpr) f).collect(Collectors.toList()));
  }

  @Override
  public Expression reduce() {
    return new PiecewiseExpr(exprs.stream().map(Expression::reduce).map(f -> (ConditionalExpr) f)
        .collect(Collectors.toList()));
  }

  @Override
  public String asString() {
    return "(" + exprs.stream().map(Expression::asString).collect(Collectors.joining(", ")) + ")";
  }
}
