package cs3500.animator.model;

import cs3500.animator.model.motions.MotionViewModel;
import cs3500.animator.model.shapes.ShapeViewModel;
import java.util.List;

/**
 * Represents the view model of an animation.
 */
public interface AnimationViewModel {

  /**
   * Gets the shape with the given name.
   *
   * @param name of the shape.
   * @return the shape.
   */
  ShapeViewModel getShape(String name);

  /**
   * Gets the shape with the given name at the given frame.
   *
   * @param name  of the shape.
   * @param frame of the shape state.
   * @return the shape.
   */
  ShapeViewModel getShapeAtFrame(String name, int frame);

  /**
   * Gets all the shapes of the animation.
   *
   * @return the shapes.
   */
  List<ShapeViewModel> getShapes();

  /**
   * Gets the shapes at the given frame.
   *
   * @param frame of the shapes.
   * @return the shapes.
   */
  List<ShapeViewModel> getShapesAtCurrentFrame(int frame);

  /**
   * Gets all the motions.
   *
   * @return the motions.
   */
  List<MotionViewModel> getMotions();

  /**
   * Gets all the motions of the given shape name.
   *
   * @param name of the shape.
   * @return the motions of the shape.
   */
  List<MotionViewModel> getMotionsOf(String name);

  /**
   * Gets the leftmost x value of the canvas.
   *
   * @return the leftmost x.
   */
  int getCanvasX();

  /**
   * Gets the topmost y value of the canvas.
   *
   * @return the topmost y.
   */
  int getCanvasY();

  /**
   * Gets the width of the canvas.
   *
   * @return the width of the canvas.
   */
  int getCanvasWidth();

  /**
   * Gets the height of the canvas.
   *
   * @return the height of the canvas.
   */
  int getCanvasHeight();

}
