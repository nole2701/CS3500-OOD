package cs3500.animator.model.shapes;

import java.awt.Color;

/**
 * Represents the model of a shape.
 */
public interface ShapeModel extends ShapeViewModel {

  /**
   * Moves the shape by the given change in x and change in y.
   *
   * @param deltaX the change of x.
   * @param deltaY the change of y.
   */
  void move(int deltaX, int deltaY);

  /**
   * Scales the shape by the given change in width and change in height. If the change in width or
   * height causes their attributes to become non-positive, just set them to 1.
   *
   * @param deltaW the change of width.
   * @param deltaH the change of height.
   */
  void scale(int deltaW, int deltaH);

  /**
   * Re-colors the shape by the given change in red value, green value, and blue value. If the
   * change causes the color value to be invalid, set it to either 0 or 255.
   *
   * @param deltaR the change in red value.
   * @param deltaG the change in green value.
   * @param deltaB the change in blue value.
   */
  void color(int deltaR, int deltaG, int deltaB);

  /**
   * Teleports the shape to the given positives with the given attributes.
   *
   * @param x     new x-coordinate of the shape.
   * @param y     new y-coordinate of the shape.
   * @param w     new width of the shape.
   * @param h     new height of the shape.
   * @param color new color of the shape.
   */
  void teleport(int x, int y, int w, int h, Color color);

}
