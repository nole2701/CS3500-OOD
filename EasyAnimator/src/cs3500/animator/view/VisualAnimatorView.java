package cs3500.animator.view;

import cs3500.animator.model.shapes.ShapeViewModel;

/**
 * Represents an advanced animator view. This view is useful for visual gui animation view.
 */
public interface VisualAnimatorView extends AnimatorView {

  /**
   * Allows for the drawing of the given shape.
   *
   * @param shape the shape to draw.
   * @throws IllegalArgumentException if the shape was null.
   */
  void drawShape(ShapeViewModel shape) throws IllegalArgumentException;

  /**
   * Re-paints the GUI view.
   */
  void refresh();
}
