package org.handrinp.diffyq;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Graph {
  private List<Expression> functions;
  private GraphSettings settings;

  public Graph(GraphSettings settings) {
    functions = new ArrayList<>();
    this.settings = settings;
  }

  public void addFunction(Expression f) {
    functions.add(f);
  }

  public Graph() {
    this(new GraphSettings.Builder().build());
  }

  private static int clamp(int n, int lo, int hi) {
    return Math.min(hi, Math.max(lo, n));
  }

  private static double clamp(double n, double lo, double hi) {
    return Math.min(hi, Math.max(lo, n));
  }

  private int xCoord(double x) {
    x = clamp(x, settings.getMinX(), settings.getMaxX());
    return (int) Math.round((settings.getWidth() - 1) * (x - settings.getMinX())
        / (settings.getMaxX() - settings.getMinX()));
  }

  private double xValue(int c) {
    double prop = (double) c / settings.getWidth();
    return (1 - prop) * settings.getMinX() + prop * settings.getMaxX();
  }

  private int yCoord(double y) {
    y = clamp(y, settings.getMinY(), settings.getMaxY());
    return (int) Math.round((settings.getHeight() - 1)
        * (1 - (y - settings.getMinY()) / (settings.getMaxY() - settings.getMinY())));
  }

  private BufferedImage drawBackground() {
    final int fg = settings.getForeground().getRGB();
    final int bg = settings.getBackground().getRGB();
    BufferedImage img =
        new BufferedImage(settings.getWidth(), settings.getHeight(), BufferedImage.TYPE_INT_RGB);

    // draw background
    for (int r = 0; r < img.getHeight(); ++r) {
      for (int c = 0; c < img.getWidth(); ++c) {
        img.setRGB(c, r, bg);
      }
    }

    // draw x axis
    if (settings.getMinX() < 0 && 0 < settings.getMaxX()) {
      int r0 = yCoord(0);

      for (int r = clamp(r0 - 1, 0, settings.getHeight()); r <= clamp(r0 + 1, 0,
          settings.getHeight()); ++r) {
        for (int c = 0; c < settings.getWidth(); ++c) {
          img.setRGB(c, r, fg);
        }
      }
    }

    // draw y axis
    if (settings.getMinY() < 0 && 0 < settings.getMaxY()) {
      int c0 = xCoord(0);

      for (int c = clamp(c0 - 1, 0, settings.getWidth()); c <= clamp(c0 + 1, 0,
          settings.getWidth()); ++c) {
        for (int r = 0; r < settings.getHeight(); ++r) {
          img.setRGB(c, r, fg);
        }
      }
    }

    // draw vertical grid lines
    for (double x = settings.getMinX() + settings.getMinX() % settings.getStepX(); x < settings
        .getMaxX(); x += settings.getStepX()) {
      int c = xCoord(x);

      for (int r = 0; r < img.getHeight(); ++r) {
        img.setRGB(c, r, fg);
      }
    }

    // draw horizontal grid lines
    for (double y = settings.getMinY() + settings.getMinY() % settings.getStepY(); y < settings
        .getMaxY(); y += settings.getStepY()) {
      int r = yCoord(y);

      for (int c = 0; c < img.getWidth(); ++c) {
        img.setRGB(c, r, fg);
      }
    }

    return img;
  }

  public BufferedImage plot(Expression f, BufferedImage img) {
    final int rgb = settings.getColor().getRGB();
    Integer[] rows = new Integer[img.getWidth()];

    // calculate f at each x value
    for (int c = 0; c < img.getWidth(); ++c) {
      double x = xValue(c);
      double y = f.evaluate(x);

      if (Double.isFinite(y) && y > settings.getMinY() && y < settings.getMaxY()) {
        rows[c] = yCoord(y);
      }
    }

    // draw a vertical line between neighboring points to interpolate the graph
    for (int c = 0; c < img.getWidth() - 1; ++c) {
      if (rows[c] != null && rows[c + 1] != null) {
        int rStart = clamp(Math.min(rows[c], rows[c + 1]) - 1, 0, settings.getHeight());
        int rEnd = clamp(Math.max(rows[c], rows[c + 1]) + 1, 0, settings.getHeight());

        for (int r = rStart; r <= rEnd; ++r) {
          img.setRGB(c, r, rgb);
        }
      }
    }

    return img;
  }

  public BufferedImage graph() {
    BufferedImage img = drawBackground();

    for (Expression f : functions) {
      img = plot(f, img);
    }

    return img;
  }

  public void show() {
    JFrame frame = new JFrame("DifferentiateMe");
    frame.getContentPane().setLayout(new FlowLayout());
    frame.getContentPane().add(new JLabel(new ImageIcon(graph())));
    frame.setResizable(false);
    frame.pack();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
