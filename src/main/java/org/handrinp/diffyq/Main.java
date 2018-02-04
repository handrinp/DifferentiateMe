package org.handrinp.diffyq;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.handrinp.diffyq.expression.ConditionalExpr;
import org.handrinp.diffyq.expression.PiecewiseExpr;
import org.handrinp.diffyq.expression.arithmetic.NegateExpr;

public class Main {
  public static void main(String[] args) throws IOException {
    GraphSettings settings = new GraphSettings.Builder().withDimensions(1600, 900).build();
    Graph g = new Graph(settings);
    Expression f = new PiecewiseExpr(
        Arrays.asList(new ConditionalExpr(new NegateExpr(Expression.X), x -> x < 0 && x > -1)));
    Expression df = f.derivative().reduce();
    g.addFunction(df, Color.RED);
    g.addFunction(f);
    String fileName = "graph.png";
    File desktop = new File(System.getProperty("user.home"), "Desktop");
    File file = new File(desktop, fileName);
    g.save(file);
    g.show();
  }
}
