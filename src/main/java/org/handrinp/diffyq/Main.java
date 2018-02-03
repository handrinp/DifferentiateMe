package org.handrinp.diffyq;

import java.util.Arrays;
import java.util.List;
import org.handrinp.diffyq.expression.ConstantExpr;
import org.handrinp.diffyq.expression.arithmetic.FractionExpr;
import org.handrinp.diffyq.expression.arithmetic.NegateExpr;
import org.handrinp.diffyq.expression.arithmetic.ProductExpr;
import org.handrinp.diffyq.expression.arithmetic.SumExpr;
import org.handrinp.diffyq.expression.exponential.PowerExpr;
import org.handrinp.diffyq.expression.trig.CosExpr;
import org.handrinp.diffyq.expression.trig.SinExpr;

public class Main {
  public static void main(String[] args) {
    Graph g = new Graph();
    Expression f = new ProductExpr(Expression.X, Expression.X);
    Expression df = f.derivative();
    Expression d2f = df.derivative();
    g.addFunction(f);
    g.addFunction(df);
    g.addFunction(d2f);
    g.show();
  }

  public static IndexedConsumer<Expression> testFunction = (i, f, x) -> {
    String n = i == 0 ? "" : ("" + i);
    System.out.println("f" + n + "(x) = " + f);
    f = f.reduce();
    System.out.println(" = " + f);
    Expression df = f.derivative().reduce();
    System.out.println("f" + n + "(" + x + ") = " + f.evaluate(x));
    System.out.println("f" + n + "'(x) = " + df);
    System.out.println();
  };

  public static void runTestSuite(double x0) {
    Expression three = new ConstantExpr(3);
    Expression x = Expression.X;
    Expression add = new SumExpr(three, x, Expression.ZERO);
    Expression neg = new NegateExpr(x);
    Expression inv = new FractionExpr(Expression.ONE, x);
    Expression prod = new ProductExpr(three, x, x);
    Expression sin = new SinExpr(x);
    Expression cosxx = new CosExpr(new ProductExpr(x, x));
    Expression exp = new PowerExpr(new ConstantExpr(Math.E), x);
    Expression xx = new PowerExpr(x, new ConstantExpr(2));
    Expression xxx = new PowerExpr(x, new ConstantExpr(3));
    Expression third = new FractionExpr(new FractionExpr(Expression.ONE, new ConstantExpr(3)),
        new FractionExpr(Expression.ONE, new ConstantExpr(2)));
    Expression fun1 = new SinExpr(inv);

    forEachWithIndex(
        Arrays.asList(three, x, add, neg, inv, prod, sin, cosxx, exp, xx, xxx, third, fun1),
        testFunction, x0);
  }

  private static interface IndexedConsumer<E> {
    public void consume(int i, E e, double x);
  }

  private static <E> void forEachWithIndex(List<E> list, IndexedConsumer<E> f, double x) {
    for (int i = 0; i < list.size(); ++i) {
      f.consume(i, list.get(i), x);
    }
  }
}
