package org.handrinp.diffyq.gfx;

import java.awt.Color;

public class GraphSettings {
  // image settings
  private final int width;
  private final int height;

  // color settings
  private final Color background;
  private final Color foreground;
  private final Color color;

  // x settings
  private final double minX;
  private final double maxX;
  private final double stepX;

  // y settings
  private final double minY;
  private final double maxY;
  private final double stepY;

  private GraphSettings(int width, int height, Color background, Color foreground, Color color,
      double minX, double maxX, double stepX, double minY, double maxY, double stepY) {
    this.width = width;
    this.height = height;

    this.background = background;
    this.foreground = foreground;
    this.color = color;

    this.minX = minX;
    this.maxX = maxX;
    this.stepX = stepX;

    this.minY = minY;
    this.maxY = maxY;
    this.stepY = stepY;
  }

  public static class Builder {
    // image settings
    private int width;
    private int height;

    // color settings
    private Color background;
    private Color foreground;
    private Color color;

    // x settings
    private double minX;
    private double maxX;
    private double stepX;

    // y settings
    private double minY;
    private double maxY;
    private double stepY;

    public Builder() {
      width = 800;
      height = 600;

      background = Color.WHITE;
      foreground = Color.BLACK;
      color = Color.BLUE;

      minX = -10.0;
      maxX = 10.0;
      stepX = 1.0;

      minY = -7.5;
      maxY = 7.5;
      stepY = 1.0;
    }

    public Builder withDimensions(int width, int height) {
      this.width = width;
      this.height = height;
      return this;
    }

    public Builder withColors(Color background, Color foreground, Color color) {
      this.background = background;
      this.foreground = foreground;
      this.color = color;
      return this;
    }

    public Builder withBounds(double minX, double maxX, double stepX, double minY, double maxY,
        double stepY) {
      this.minX = minX;
      this.maxX = maxX;
      this.stepX = stepX;
      this.minY = minY;
      this.maxY = maxY;
      this.stepY = stepY;
      return this;
    }

    public Builder withBounds(double minX, double maxX, double minY, double maxY) {
      return withBounds(minX, maxX, (maxX - minX) * 40.0 / width, minY, maxY,
          (maxY - minY) * 40.0 / height);
    }

    public GraphSettings build() {
      return new GraphSettings(width, height, background, foreground, color, minX, maxX, stepX,
          minY, maxY, stepY);
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Color getBackground() {
    return background;
  }

  public Color getForeground() {
    return foreground;
  }

  public Color getColor() {
    return color;
  }

  public double getMinX() {
    return minX;
  }

  public double getMaxX() {
    return maxX;
  }

  public double getStepX() {
    return stepX;
  }

  public double getMinY() {
    return minY;
  }

  public double getMaxY() {
    return maxY;
  }

  public double getStepY() {
    return stepY;
  }
}
