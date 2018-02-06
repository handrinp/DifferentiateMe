package org.handrinp.diffyq.gfx;

import java.awt.Color;

/**
 * settings class for the Graph object, constructed by its Builder which implements the fluent API
 * 
 * @author handrinp
 */
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

  /**
   * construct a graph settings verbosely
   * 
   * should only be used by the Builder
   * 
   * @param width width
   * @param height height
   * @param background background color
   * @param foreground foreground color
   * @param color default color
   * @param minX minX
   * @param maxX maxX
   * @param stepX stepX
   * @param minY minY
   * @param maxY maxY
   * @param stepY stepY
   */
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

  /**
   * its methods to construct a GraphSettings, and implement the fluent API
   * 
   * @author handrinp
   */
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

    /**
     * create a Builder with the default GraphSettings values
     */
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

    /**
     * set the dimensions for the GraphSettings
     * 
     * @param width width
     * @param height height
     * @return the Builder on which it was called
     */
    public Builder withDimensions(int width, int height) {
      this.width = width;
      this.height = height;
      return this;
    }

    /**
     * set the colors for the GraphSettings
     * 
     * @param background the background color for the graph
     * @param foreground the color of the grid lines, axes, and labels for the graph
     * @param color the default color for expressions plotted on the graph
     * @return the Builder on which it was called
     */
    public Builder withColors(Color background, Color foreground, Color color) {
      this.background = background;
      this.foreground = foreground;
      this.color = color;
      return this;
    }

    /**
     * set the graph bounds for the GraphSettings
     * 
     * @param minX minX
     * @param maxX maxX
     * @param stepX stepX
     * @param minY minY
     * @param maxY maxY
     * @param stepY stepY
     * @return the Builder on which it was called
     */
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

    /**
     * set the graph bounds for the GraphSettings, but automatically calculate the x and y steps
     * 
     * @param minX minX
     * @param maxX maxX
     * @param minY minY
     * @param maxY maxY
     * @return the Builder on which it was called
     */
    public Builder withBounds(double minX, double maxX, double minY, double maxY) {
      return withBounds(minX, maxX, (maxX - minX) * 40.0 / width, minY, maxY,
          (maxY - minY) * 40.0 / height);
    }

    /**
     * using the given settings, construct a new GraphSettings object verbosely
     * 
     * @return the GraphSettings built by this builder
     */
    public GraphSettings build() {
      return new GraphSettings(width, height, background, foreground, color, minX, maxX, stepX,
          minY, maxY, stepY);
    }
  }

  /**
   * the width of the image in pixels
   * 
   * @return width
   */
  public int getWidth() {
    return width;
  }

  /**
   * the height of the image in pixels
   * 
   * @return height
   */
  public int getHeight() {
    return height;
  }

  /**
   * the background color of the image
   * 
   * @return background
   */
  public Color getBackground() {
    return background;
  }

  /**
   * the foreground color of the image (grid lines, axes, labels)
   * 
   * @return foreground
   */
  public Color getForeground() {
    return foreground;
  }

  /**
   * the default function color of the image
   * 
   * @return color
   */
  public Color getColor() {
    return color;
  }

  /**
   * the leftmost x value of the graph
   * 
   * @return minX
   */
  public double getMinX() {
    return minX;
  }

  /**
   * the rightmost x value of the graph
   * 
   * @return maxX
   */
  public double getMaxX() {
    return maxX;
  }

  /**
   * the distance between vertical grid lines
   * 
   * @return stepX
   */
  public double getStepX() {
    return stepX;
  }

  /**
   * the bottom y value of the graph
   * 
   * @return minY
   */
  public double getMinY() {
    return minY;
  }

  /**
   * the top y value of the graph
   * 
   * @return maxY
   */
  public double getMaxY() {
    return maxY;
  }

  /**
   * the distance between horizontal grid lines
   * 
   * @return stepY
   */
  public double getStepY() {
    return stepY;
  }
}
