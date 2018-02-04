package org.handrinp.diffyq;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import org.handrinp.diffyq.expression.trig.TanExpr;
import org.handrinp.diffyq.gfx.Graph;
import org.handrinp.diffyq.gfx.GraphSettings;

public class Main {
  public static void main(String[] args) throws IOException {
    GraphSettings settings = new GraphSettings.Builder().withDimensions(500, 500)
        .withBounds(-Math.PI, Math.PI, -10, 10).build();
    Graph g = new Graph(settings);
    Expression f = new TanExpr(Expression.X);
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
