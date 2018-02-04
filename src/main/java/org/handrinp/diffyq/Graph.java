package org.handrinp.diffyq;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Graph {
  private List<Function> functions;
  private GraphSettings settings;

  public Graph(GraphSettings settings) {
    functions = new ArrayList<>();
    this.settings = settings;
  }

  public void addFunction(Expression f, Color color) {
    functions.add(new Function(f, color));
  }

  public void addFunction(Expression f) {
    addFunction(f, settings.getColor());
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
    for (double x = settings.getMinX() - settings.getMinX() % settings.getStepX(); x < settings
        .getMaxX(); x += settings.getStepX()) {
      int c = xCoord(x);

      for (int r = 0; r < img.getHeight(); ++r) {
        img.setRGB(c, r, fg);
      }
    }

    // draw horizontal grid lines
    for (double y = settings.getMinY() - settings.getMinY() % settings.getStepY(); y < settings
        .getMaxY(); y += settings.getStepY()) {
      int r = yCoord(y);

      for (int c = 0; c < img.getWidth(); ++c) {
        img.setRGB(c, r, fg);
      }
    }

    return img;
  }

  public BufferedImage plot(Expression f, BufferedImage img, Color color) {
    final int rgb = color.getRGB();
    final int maxHeight = settings.getHeight() - 1;
    final int maxWidth = settings.getWidth() - 1;
    Integer[] ys = new Integer[img.getWidth()];

    // calculate f at each x value
    for (int c = 0; c < img.getWidth(); ++c) {
      double x = xValue(c);
      double y = f.evaluate(x);

      // handle NaN and +/- infinity
      if (Double.isFinite(y)) {
        ys[c] = yCoord(y);
      }
    }

    // draw a vertical line between neighboring points to interpolate the graph
    for (int c0 = 0; c0 < img.getWidth() - 1; ++c0) {
      boolean nonNull = ys[c0] != null && ys[c0 + 1] != null;
      boolean bothMin = nonNull && ys[c0] == 0 && ys[c0 + 1] == 0;
      boolean bothMax = nonNull && ys[c0] == maxHeight && ys[c0 + 1] == maxHeight;

      // check if the points should be drawn
      if (nonNull && !(bothMin || bothMax)) {
        // thicken the line by making its bounds extra wide/tall
        int rStart = clamp(Math.min(ys[c0], ys[c0 + 1]) - 1, 0, maxHeight);
        int rEnd = clamp(Math.max(ys[c0], ys[c0 + 1]) + 1, 0, maxHeight);
        int cStart = clamp(c0 - 1, 0, maxWidth);
        int cEnd = clamp(c0 + 1, 0, maxWidth);

        // draw the line
        for (int r = rStart; r <= rEnd; ++r) {
          for (int c = cStart; c <= cEnd; ++c) {
            img.setRGB(c, r, rgb);
          }
        }
      }
    }

    return img;
  }

  public BufferedImage graph() {
    BufferedImage img = drawBackground();

    for (Function f : functions) {
      img = plot(f.getF(), img, f.getColor());
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

  public void save(File file) throws IOException {
    ImageIO.write(graph(), "png", file);
  }

  private class Function {
    private Expression f;
    private Color color;

    public Function(Expression f, Color color) {
      this.f = f;
      this.color = color;
    }

    public Expression getF() {
      return f;
    }

    public Color getColor() {
      return color;
    }
  }
}
