package cs3500.animator.model.shapes.implementations;

import cs3500.animator.model.shapes.ShapeModel;
import cs3500.animator.model.shapes.ShapeType;
import cs3500.animator.model.shapes.ShapeViewModel;
import java.awt.Color;
import java.util.Objects;

/**
 * Represents a basic shape. A basic shape can be of various types, but they mostly have the same
 * attributes. Therefore, no need to distinguish different shapes with different classes.
 */
public class BasicShape implements ShapeModel {

  private final String name;
  private int x;
  private int y;
  private int width;
  private int height;
  private Color color;
  private final int vertices;
  private final ShapeType type;

  /**
   * Constructs a basic shape with the given attributes.
   *
   * @param name     of the shape.
   * @param vertices count for the shape.
   * @param type     of the shape.
   * @param x        -coordinate of the shape.
   * @param y        -coordinate of the shape.
   * @param width    of the shape.
   * @param height   of the shape.
   * @param color    of the shape.
   * @throws IllegalArgumentException if the length of the name is 0, if the type of the shape is
   *                                  null, if the type is ELLIPSE but the vertices count is not 0,
   *                                  if the type if RECTANGLE but the vertices count is not 4, if
   *                                  the type is POLYGON but the vertices count is less than 3, if
   *                                  the width of the shape is non-positive, if the height of the
   *                                  shape is non-positive, if the color of the shape is null.
   */
  public BasicShape(String name, int vertices, ShapeType type, int x, int y, int width, int height,
      Color color)
      throws IllegalArgumentException {
    if (name.length() == 0) {
      throw new IllegalArgumentException("The shape must be given a non-empty name.");
    }

    this.name = name;

    if (type == null) {
      throw new IllegalArgumentException("The given shape type is null.");
    }

    if (type == ShapeType.ELLIPSE && vertices != 0) {
      throw new IllegalArgumentException("For an Ellipse, the number of sides must be 0.");
    }

    if (type == ShapeType.RECTANGLE && vertices != 4) {
      throw new IllegalArgumentException("For a Rectangle, the number of sides must be 4.");
    }

    if (type == ShapeType.POLYGON && vertices < 3) {
      throw new IllegalArgumentException(
          "For a Polygon, the number of sides must be greater than or equal to 3.");
    }

    this.vertices = vertices;
    this.type = type;

    this.x = x;
    this.y = y;

    if (width <= 0) {
      throw new IllegalArgumentException("The shape must have a width that is positive.");
    }

    if (height <= 0) {
      throw new IllegalArgumentException("The shape must have a height that is positive.");
    }

    this.width = width;
    this.height = height;

    if (color == null) {
      throw new IllegalArgumentException("The given shape color is null");
    }

    this.color = new Color(color.getRGB());
  }

  /**
   * Constructs a basic shape from another.
   *
   * @param other shape to copy.
   */
  public BasicShape(ShapeViewModel other) {
    this(other.getName(), other.getVertices(), other.getType(), other.getX(), other.getY(),
        other.getWidth(), other.getHeight(),
        other.getColor());
  }

  @Override
  public void move(int deltaX, int deltaY) {
    this.x = this.x + deltaX;
    this.y = this.y + deltaY;
  }

  @Override
  public void scale(int deltaW, int deltaH) throws IllegalArgumentException {
    int newWidth = Math.max(this.width + deltaW, 1);
    int newHeight = Math.max(this.height + deltaH, 1);

    this.width = newWidth;
    this.height = newHeight;
  }

  @Override
  public void color(int deltaR, int deltaG, int deltaB) {
    int newRed = Math.min(Math.max(this.color.getRed() + deltaR, 0), 255);
    int newGreen = Math.min(Math.max(this.color.getGreen() + deltaG, 0), 255);
    int newBlue = Math.min(Math.max(this.color.getBlue() + deltaB, 0), 255);

    this.color = new Color(newRed, newGreen, newBlue);
  }

  @Override
  public void teleport(int x, int y, int w, int h, Color color) {
    this.x = x;
    this.y = y;

    if (w <= 0) {
      throw new IllegalArgumentException("The given w for width must be positive.");
    }

    if (h <= 0) {
      throw new IllegalArgumentException("The given h for width must be positive.");
    }

    this.width = w;
    this.height = h;

    if (color == null) {
      throw new IllegalArgumentException("The given color must not be null.");
    }

    this.color = color;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public Color getColor() {
    return new Color(this.color.getRGB());
  }

  @Override
  public int getRed() {
    return this.color.getRed();
  }

  @Override
  public int getGreen() {
    return this.color.getGreen();
  }

  @Override
  public int getBlue() {
    return this.color.getBlue();
  }

  @Override
  public ShapeType getType() {
    return this.type;
  }

  @Override
  public int getVertices() {
    return this.vertices;
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(this.name.hashCode(), this.x, this.y, this.width, this.height, this.color.hashCode(),
            this.vertices, this.type.hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof BasicShape)) {
      return false;
    }

    BasicShape other = (BasicShape) obj;

    return this.name.equals(other.name) && this.x == other.x && this.y == other.y
        && this.width == other.width && this.height == other.height && this.color
        .equals(other.color) && this.type == other.type && this.vertices == other.vertices;
  }

  @Override
  public String toString() {
    return String.format("shape %s %s", this.name, this.type.toString());
  }

  @Override
  public int compareTo(ShapeViewModel o) {
    Objects.requireNonNull(o);
    return this.type.compareTo(o.getType());
  }
}
