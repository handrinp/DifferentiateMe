package org.handrinp.diffyq;

import java.awt.Color;
import java.util.Arrays;
import org.handrinp.diffyq.expression.PiecewiseExpr;
import org.handrinp.diffyq.expression.arithmetic.ProductExpr;

public class Main {
  public static void main(String[] args) {
    Graph g = new Graph();
    Expression left = new ProductExpr(Expression.X, Expression.X);
    Expression right = Expression.X;
    Expression pwf =
        new PiecewiseExpr(Arrays.asList(left, right), Arrays.asList(x -> x < 0, x -> x > 0));
    Expression dpwf = pwf.derivative().reduce();
    g.addFunction(pwf);
    g.addFunction(dpwf, Color.RED);
    g.show();
  }
}
