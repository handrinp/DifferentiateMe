package org.handrinp.diffyq.expression.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.expression.ConstantExpr;

public class SumExpr extends Expression {
  private List<Expression> terms;

  public SumExpr(List<Expression> terms) {
    this.terms = new ArrayList<>();

    terms.forEach(term -> {
      if (term instanceof SumExpr) {
        this.terms.addAll(((SumExpr) term).terms);
      } else {
        this.terms.add(term);
      }
    });
  }

  public SumExpr(Expression... terms) {
    this(Arrays.asList(terms));
  }

  public SumExpr add(Expression f) {
    List<Expression> newTerms = new ArrayList<>();
    newTerms.addAll(terms);

    if (f instanceof SumExpr) {
      newTerms.addAll(((SumExpr) f).terms);
    } else {
      newTerms.add(f);
    }

    return new SumExpr(newTerms);
  }

  @Override
  public Expression derivative() {
    return new SumExpr(terms.stream().map(Expression::derivative).collect(Collectors.toList()));
  }

  @Override
  public double evaluate(double x) {
    return terms.stream().mapToDouble(f -> f.evaluate(x)).sum();
  }

  @Override
  public Expression reduce() {
    List<Expression> newTerms =
        terms.stream().filter(f -> !(f instanceof ConstantExpr && ((ConstantExpr) f).isZero()))
            .map(Expression::reduce).sorted(Comparator.comparing(f -> f.getClass().getName()))
            .collect(Collectors.toList());

    // TODO combine like terms
    for (int i = 0; i < newTerms.size() - 1; ++i) {
      Expression u = newTerms.remove(i);

      if (u instanceof SumExpr) {
        newTerms.addAll(i--, ((SumExpr) u).terms);
      } else if (u instanceof ConstantExpr) {
        Expression v = newTerms.remove(i);

        if (v instanceof ConstantExpr) {
          newTerms.add(i--,
              new ConstantExpr(((ConstantExpr) u).getValue() + ((ConstantExpr) v).getValue()));
        } else {
          newTerms.add(i, v);
          newTerms.add(i, u);
        }
      } else {
        newTerms.add(i, u);
      }
    }

    switch (newTerms.size()) {
      case 0:
        return Expression.ZERO;
      case 1:
        return newTerms.get(0);
    }

    newTerms =
        newTerms.stream().filter(f -> !(f instanceof ConstantExpr && ((ConstantExpr) f).isZero()))
            .map(Expression::reduce).sorted(Comparator.comparing(f -> f.getClass().getName()))
            .collect(Collectors.toList());

    return new SumExpr(newTerms);
  }

  @Override
  public String asString() {
    return "(" + terms.stream().map(Expression::asString).collect(Collectors.joining(" + ")) + ")";
  }
}