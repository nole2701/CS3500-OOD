package cs3500.animator.view;

import cs3500.animator.model.AnimationViewModel;
import cs3500.animator.model.motions.MotionViewModel;
import cs3500.animator.model.shapes.ShapeViewModel;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Represents the SVGView. Shares many commonalities with the SVG view. Basic output for
 * animations.
 */
public class TextualView implements AnimatorView {

  private final AnimationViewModel model;
  private final OutputStream appendable;
  private final double ticksPerSecond;

  /**
   * Constructs an Textual view.
   *
   * @param model          of the animation.
   * @param appendable     to append to.
   * @param ticksPerSecond of the animation.
   * @throws IllegalArgumentException if the given ticksPerSecond were non-positive, if the model
   *                                  was null, if the appendable was null, if the width was
   *                                  non-positive, if the height was non-positive.
   */
  public TextualView(AnimationViewModel model, OutputStream appendable, double ticksPerSecond) {
    if (ticksPerSecond <= 0) {
      throw new IllegalArgumentException("The given ticks per second must be positive.");
    }

    this.ticksPerSecond = ticksPerSecond;

    if (model == null) {
      throw new IllegalArgumentException("The given view model must not be null.");
    }

    this.model = model;

    if (appendable == null) {
      throw new IllegalArgumentException("The given appendable must not be null.");
    }

    this.appendable = appendable;
  }

  @Override
  public void render() throws IOException {
    appendable.write(this.toString().getBytes());
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    // Display canvas dimensions:
    builder.append(String
        .format("canvas %3d %3d %3d %3d\n", model.getCanvasX(), model.getCanvasY(),
            model.getCanvasWidth(),
            model.getCanvasHeight()));

    for (ShapeViewModel shape : model.getShapes()) {
      builder.append(shape.toString()).append("\n");
    }

    for (ShapeViewModel shape : model.getShapes()) {
      for (MotionViewModel motion : model.getMotions()) {
        if (motion.getShape().equals(shape)) {
          builder.append(String
              .format(
                  "motion %s %3.2f %3d %3d %3d %3d %3d %3d %3d    "
                      + " %3.2f %3d %3d %3d %3d %3d %3d %3d\n",
                  shape.getName(),
                  motion.getStartTick() / this.ticksPerSecond, motion.getStartX(),
                  motion.getStartY(),
                  motion.getStartWidth(), motion.getStartHeight(),
                  motion.getStartColor().getRed(), motion.getStartColor().getGreen(),
                  motion.getStartColor().getBlue(),
                  motion.getEndTick() / this.ticksPerSecond, motion.getEndX(), motion.getEndY(),
                  motion.getEndHeight(), motion.getEndHeight(),
                  motion.getEndColor().getRed(), motion.getEndColor().getGreen(),
                  motion.getEndColor().getBlue()));
        }
      }
    }

    return builder.toString();
  }
}
