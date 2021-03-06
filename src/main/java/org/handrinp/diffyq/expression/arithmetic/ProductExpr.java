package org.handrinp.diffyq.expression.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.handrinp.diffyq.Constants;
import org.handrinp.diffyq.Expression;
import org.handrinp.diffyq.expression.ConstantExpr;

/**
 * arithmetic product of expressions
 * 
 * @author handrinp
 */
public class ProductExpr extends Expression {
  private List<Expression> terms;

  /**
   * construct a product with the given terms as factors
   * 
   * @param terms list of factors to multiply
   */
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

  /**
   * construct a product with the given terms as factors
   * 
   * @param terms array of factors to multiply
   */
  public ProductExpr(Expression... terms) {
    this(Arrays.asList(terms));
  }

  /**
   * multiply a factor with the product
   * 
   * @param term factor
   * @return a new product with all the previous factors and the new one
   */
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
      ProductExpr product = new ProductExpr();

      for (int j = 0; j < i; ++j) {
        product = product.multiply(terms.get(j));
      }

      product = product.multiply(derivatives.get(i));

      for (int j = i + 1; j < terms.size(); ++j) {
        product = product.multiply(terms.get(j));
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
      return Constants.ZERO;
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

    // combine constant terms
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

    // TODO: combine variable terms

    // combine fraction terms
    if (newTerms.stream().anyMatch(term -> term instanceof FractionExpr)) {
      FractionExpr newFraction = new FractionExpr(Constants.ONE, Constants.ONE);

      for (Expression term : newTerms) {
        newFraction = newFraction.multiply(term);
      }

      return newFraction.reduce();
    }

    // for (int i = 0; i < newTerms.size() - 1; ++i) {
    // Expression e1 = newTerms.remove(i);
    //
    // if (e1 instanceof FractionExpr) {
    // FractionExpr fe1 = (FractionExpr) e1;
    // Expression e2 = newTerms.remove(i);
    //
    // if (e2 instanceof FractionExpr) {
    // FractionExpr fe2 = (FractionExpr) e2;
    // newTerms.add(i--, new FractionExpr(new ProductExpr(fe1.getU(), fe2.getU()).reduce(),
    // new ProductExpr(fe1.getV(), fe2.getV()).reduce()));
    // } else {
    // newTerms.add(i, e2);
    // newTerms.add(i, e1);
    // }
    // } else {
    // newTerms.add(i, e1);
    // }
    // }

    // product of 0 terms is 1
    if (newTerms.isEmpty())
      return Constants.ONE;

    // product of 1 term is just the term
    if (newTerms.size() == 1)
      return newTerms.get(0);

    return new ProductExpr(newTerms);
  }

  @Override
  public String asString() {
    return "(" + terms.stream().map(Expression::asString).collect(Collectors.joining(" * ")) + ")";
  }

  @Override
  public int hash() {
    int hash = 1;

    for (Expression e : terms)
      hash = 37 * hash + e.hash();

    return hash;
  }
}
