package cs3500.animator.model.motions;

import cs3500.animator.model.shapes.ShapeViewModel;
import java.awt.Color;
import java.util.List;

/**
 * Represents a read-only version of a motion model.
 */
public interface MotionViewModel {

  /**
   * Gets the shape of the motion.
   *
   * @return the shape.
   */
  ShapeViewModel getShape();

  /**
   * Gets the start tick of the motion.
   *
   * @return the start tick.
   */
  int getStartTick();

  /**
   * Gets the end tick of the motion.
   *
   * @return the end tick.
   */
  int getEndTick();

  /**
   * Gets the start x-coordinate of the motion.
   *
   * @return the start x.
   */
  int getStartX();

  /**
   * Gets the end x-coordinate of the motion.
   *
   * @return the end x.
   */
  int getEndX();

  /**
   * Gets the start y-coordinate of the motion.
   *
   * @return the start y.
   */
  int getStartY();

  /**
   * Gets the end y-coordinate of the motion.
   *
   * @return the end y.
   */
  int getEndY();

  /**
   * Gets the start width of the motion.
   *
   * @return the start width.
   */
  int getStartWidth();

  /**
   * Gets the end width of the motion.
   *
   * @return the end width.
   */
  int getEndWidth();

  /**
   * Gets the start height of the motion.
   *
   * @return the start height.
   */
  int getStartHeight();

  /**
   * Gets the end height of the motion.
   *
   * @return the end height.
   */
  int getEndHeight();

  /**
   * Gets the start color of the motion.
   *
   * @return the start color.
   */
  Color getStartColor();

  /**
   * Gets the end color of the motion.
   *
   * @return the end color.
   */
  Color getEndColor();

  /**
   * Gets the list of types that this motion contains.
   *
   * @return the list of types of the motion.
   */
  List<MotionType> getType();

}
