package org.handrinp.diffyq.expression.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.expression.ConstantExpr;

public class ProductExpr extends Expression {
  private List<Expression> terms;

  public ProductExpr(List<Expression> terms) {
    this.terms = new ArrayList<>();

    terms.forEach(term -> {
      if (term instanceof ProductExpr) {
        this.terms.addAll(((ProductExpr) term).terms);
      } else {
        this.terms.add(term);
      }
    });
  }

  public ProductExpr(Expression... terms) {
    this(Arrays.asList(terms));
  }

  public ProductExpr multiply(Expression term) {
    List<Expression> newTerms = new ArrayList<>();
    newTerms.addAll(terms);

    if (term instanceof ProductExpr) {
      newTerms.addAll(((ProductExpr) term).terms);
    } else {
      newTerms.add(term);
    }

    return new ProductExpr(newTerms);
  }

  @Override
  public double evaluate(double x) {
    return terms.stream().mapToDouble(f -> f.evaluate(x)).reduce(1, (lhs, rhs) -> lhs * rhs);
  }

  @Override
  public Expression derivative() {
    List<Expression> derivatives =
        terms.stream().map(Expression::derivative).collect(Collectors.toList());
    SumExpr sum = new SumExpr();

    for (int i = 0; i < terms.size(); ++i) {
      ProductExpr product = new ProductExpr(derivatives.get(i));

      for (int j = 0; j < terms.size(); ++j) {
        if (i != j) {
          product = product.multiply(terms.get(j));
        }
      }

      sum = sum.add(product);
    }

    return sum;
  }

  @Override
  public Expression reduce() {
    // if any term is 0, return 0
    if (terms.stream().filter(f -> f instanceof ConstantExpr && ((ConstantExpr) f).isZero())
        .findAny().isPresent()) {
      return Expression.ZERO;
    }

    // if all terms are constant, reduce this product to a constant
    if (!terms.stream().filter(f -> !(f instanceof ConstantExpr)).findAny().isPresent()) {
      return new ConstantExpr(evaluate(0)).reduce();
    }

    // reduce each term, removing any ones
    List<Expression> newTerms =
        terms.stream().filter(f -> !(f instanceof ConstantExpr && ((ConstantExpr) f).isOne()))
            .map(Expression::reduce).sorted(Comparator.comparing(f -> f.getClass().getName()))
            .collect(Collectors.toList());

    for (int i = 0; i < newTerms.size() - 1; ++i) {
      Expression u = newTerms.remove(i);

      if (u instanceof ProductExpr) {
        newTerms.addAll(i--, ((ProductExpr) u).terms);
      } else if (u instanceof ConstantExpr) {
        Expression v = newTerms.remove(i);

        if (v instanceof ConstantExpr) {
          newTerms.add(i--,
              new ConstantExpr(((ConstantExpr) u).getValue() * ((ConstantExpr) v).getValue()));
        } else {
          newTerms.add(i, v);
          newTerms.add(i, u);
        }
      } else {
        newTerms.add(i, u);
      }
    }

    // product of 0 terms is 1
    if (newTerms.isEmpty())
      return Expression.ONE;

    // product of 1 term is just the term
    if (newTerms.size() == 1)
      return newTerms.get(0);

    // TODO combine like terms
    return new ProductExpr(newTerms);
  }

  @Override
  public String asString() {
    return "(" + terms.stream().map(Expression::asString).collect(Collectors.joining(" * ")) + ")";
  }
}
