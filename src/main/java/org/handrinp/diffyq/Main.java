package org.handrinp.diffyq;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import org.handrinp.diffyq.expression.arithmetic.FractionExpr;
import org.handrinp.diffyq.expression.arithmetic.ProductExpr;
import org.handrinp.diffyq.expression.trig.CosExpr;

public class Main {
  public static void main(String[] args) throws IOException {
    GraphSettings settings =
        new GraphSettings.Builder().withDimensions(1600, 900).withBounds(-10, 10, -10, 10).build();
    Graph g = new Graph(settings);
    Expression f =
        new ProductExpr(Expression.X, new CosExpr(new FractionExpr(Expression.ONE, Expression.X)));
    Expression df = f.derivative().reduce();
    g.addFunction(df, Color.RED);
    g.addFunction(f);
    g.save(new File(System.getProperty("user.home"),
        "Desktop/graph" + System.currentTimeMillis() + ".png"));
    g.show();
  }
}
