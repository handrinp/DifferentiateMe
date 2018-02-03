package org.handrinp.diffyq.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoublePredicate;
import java.util.stream.Collectors;
import org.handrinp.diffyq.Expression;

public class PiecewiseExpr extends Expression {
  private List<Expression> exprs;
  private List<DoublePredicate> preds;

  public PiecewiseExpr(List<Expression> exprs, List<DoublePredicate> preds) {
    this.exprs = new ArrayList<>();
    this.preds = new ArrayList<>();
    this.exprs.addAll(exprs);
    this.preds.addAll(preds);
  }

  @Override
  public double evaluate(double x) {
    for (int i = 0; i < preds.size(); ++i) {
      if (preds.get(i).test(x)) {
        return exprs.get(i).evaluate(x);
      }
    }

    return Double.NaN;
  }

  @Override
  public Expression derivative() {
    return new PiecewiseExpr(
        exprs.stream().map(Expression::derivative).collect(Collectors.toList()), preds);
  }

  @Override
  public Expression reduce() {
    return new PiecewiseExpr(exprs.stream().map(Expression::reduce).collect(Collectors.toList()),
        preds);
  }

  @Override
  public String asString() {
    return "(piecewise)";
  }
}
