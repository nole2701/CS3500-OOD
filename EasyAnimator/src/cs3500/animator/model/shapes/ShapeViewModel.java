package cs3500.animator.model.shapes;

import java.awt.Color;

/**
 * Represents the view model of the shape.
 */
public interface ShapeViewModel extends Comparable<ShapeViewModel> {

  /**
   * Gets the name of the shape.
   *
   * @return the name.
   */
  String getName();

  /**
   * Gets the x-coordinate of the shape.
   *
   * @return the x-coordinate.
   */
  int getX();

  /**
   * Gets the y-coordinate of the shape.
   *
   * @return the y-coordinate.
   */
  int getY();

  /**
   * Gets the width of the shape.
   *
   * @return the width.
   */
  int getWidth();

  /**
   * Gets the height of the shape.
   *
   * @return the height.
   */
  int getHeight();

  /**
   * Gets the color of the shape.
   *
   * @return the color.
   */
  Color getColor();

  /**
   * Gets the red value of the color.
   *
   * @return the red value.
   */
  int getRed();

  /**
   * Gets the green value of the color.
   *
   * @return the green value.
   */
  int getGreen();

  /**
   * Gets the blue value of the color.
   *
   * @return the blue value.
   */
  int getBlue();

  /**
   * Gets the type of the shape.
   *
   * @return the type.
   */
  ShapeType getType();

  /**
   * Gets the vertices of the shape.
   *
   * @return the vertices.
   */
  int getVertices();

}
