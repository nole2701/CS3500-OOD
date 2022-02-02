package cs3500.animator.model;

import cs3500.animator.model.shapes.ShapeType;
import java.awt.Color;

/**
 * Represents the animation model. An animation has shapes and shapes have motions. A shape appears
 * in an animation if it has a motion. If it does not have a motion, then the shape is not drawn.
 */
public interface AnimationModel extends AnimationViewModel {

  /**
   * Adds a new shape to the animation.
   *
   * @param name     of the shape.
   * @param type     of the shape.
   * @param vertices of the shape.
   * @param x        -coordinate of the shape.
   * @param y        -coordinate of the shape.
   * @param w        width of the shape.
   * @param h        height of the shape.
   * @param color    of the shape.
   * @throws IllegalArgumentException if the name provided for the shape does not already exist.
   */
  void addShape(String name, ShapeType type, int vertices, int x, int y, int w, int h, Color color)
      throws IllegalArgumentException;

  /**
   * Removes the shape with the given name.
   *
   * @param name of the shape.
   */
  void removeShape(String name);

  /**
   * Adds a motion to the shape with the given name.
   *
   * @param name        of the shape.
   * @param startT      start tick of the shape.
   * @param endT        end tick of the shape.
   * @param startX      start x-coordinate of the shape.
   * @param endX        end x-coordinate of the shape.
   * @param startY      start y-coordinate of the shape.
   * @param endY        end y-coordinate of the shape.
   * @param startWidth  start width of the shape.
   * @param endWidth    end width of the shape.
   * @param startHeight start height of the shape.
   * @param endHeight   end height of the shape.
   * @param startColor  start color of the shape.
   * @param endColor    end color of the shape.
   * @throws IllegalArgumentException if the motion is invalid. A motion is invalid if it overlaps
   *                                  with another motion and changes the same type. Also, there
   *                                  can't be duplicate shapes. Throws if the shape does not
   *                                  exist.
   */
  void addMotion(String name, int startT, int endT, int startX, int endX, int startY, int endY,
      int startWidth, int endWidth, int startHeight, int endHeight, Color startColor,
      Color endColor) throws IllegalArgumentException;

  /**
   * Removes the motion of a shape at the given frame.
   *
   * @param name  of the shape.
   * @param frame of the motion
   */
  void removeMotion(String name, int frame);

}
