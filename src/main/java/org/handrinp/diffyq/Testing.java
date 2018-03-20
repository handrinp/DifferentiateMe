package org.handrinp.diffyq;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.handrinp.diffyq.bool.comparative.GreaterExpr;
import org.handrinp.diffyq.bool.comparative.LessEqualExpr;
import org.handrinp.diffyq.expression.ConditionalExpr;
import org.handrinp.diffyq.expression.ConstantExpr;
import org.handrinp.diffyq.expression.PiecewiseExpr;
import org.handrinp.diffyq.expression.arithmetic.FractionExpr;
import org.handrinp.diffyq.expression.arithmetic.NegateExpr;
import org.handrinp.diffyq.expression.arithmetic.ProductExpr;
import org.handrinp.diffyq.expression.arithmetic.SumExpr;
import org.handrinp.diffyq.expression.exponential.PowerExpr;
import org.handrinp.diffyq.expression.trig.CosExpr;
import org.handrinp.diffyq.expression.trig.SinExpr;
import org.handrinp.diffyq.expression.trig.TanExpr;
import org.handrinp.diffyq.gfx.Graph;
import org.handrinp.diffyq.gfx.GraphSettings;

/**
 * this class should be replaced by unit tests ASAP
 * 
 * @author handrinp
 */
public class Testing {
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

  public static void runTestSuite(double x0) throws IOException {
    Expression three = new ConstantExpr(3);
    Expression x = Constants.X;
    Expression add = new SumExpr(three, x, Constants.ZERO);
    Expression neg = new NegateExpr(x);
    Expression inv = new FractionExpr(Constants.ONE, x);
    Expression prod = new ProductExpr(three, x, x);
    Expression sin = new SinExpr(x);
    Expression cosxx = new CosExpr(new ProductExpr(x, x));
    Expression exp = new PowerExpr(new ConstantExpr(Math.E), x);
    Expression xx = new PowerExpr(x, new ConstantExpr(2));
    Expression xxx = new PowerExpr(x, new ConstantExpr(3));
    Expression third = new FractionExpr(new FractionExpr(Constants.ONE, new ConstantExpr(3)),
        new FractionExpr(Constants.ONE, new ConstantExpr(2)));
    Expression fun1 = new SinExpr(inv);
    Expression peace = new PiecewiseExpr(
        new ConditionalExpr(new TanExpr(Constants.X),
            new GreaterExpr(Constants.X, Constants.ZERO)),
        new ConditionalExpr(new SinExpr(Constants.X),
            new LessEqualExpr(Constants.X, Constants.ZERO)));

    forEachWithIndex(
        Arrays.asList(three, x, add, neg, inv, prod, sin, cosxx, exp, xx, xxx, third, fun1, peace),
        testFunction, x0);

    GraphSettings settings = new GraphSettings.Builder().withDimensions(500, 500)
        .withBounds(-Math.PI, Math.PI, -10, 10).build();
    Graph g = new Graph(settings);
    Expression f = peace;
    Expression df = f.derivative().reduce();
    g.addFunction(df, Color.RED);
    g.addFunction(f);
    File file = new File(new File(System.getProperty("user.home"), "Desktop"), "graph.png");
    g.save(file);
    g.show();
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
