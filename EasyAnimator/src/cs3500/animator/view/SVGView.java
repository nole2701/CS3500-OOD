package cs3500.animator.view;

import cs3500.animator.model.AnimationViewModel;
import cs3500.animator.model.motions.MotionType;
import cs3500.animator.model.motions.MotionViewModel;
import cs3500.animator.model.shapes.ShapeViewModel;
import cs3500.animator.view.panels.DrawingPanelPolygon;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Represents the SVGView. Shares many commonalities with the textual view. SVG views allows
 * animations created by this animator to work on other applications.
 */
public class SVGView implements AnimatorView {

  private final AnimationViewModel model;
  private final OutputStream appendable;
  private final double ticksPerSecond;

  /**
   * Constructs an SVG view.
   *
   * @param model          of the animation.
   * @param appendable     to append to.
   * @param ticksPerSecond of the animation.
   * @throws IllegalArgumentException if the given ticksPerSecond were non-positive, if the model
   *                                  was null, if the appendable was null, if the width was
   *                                  non-positive, if the height was non-positive.
   */
  public SVGView(AnimationViewModel model, OutputStream appendable, double ticksPerSecond)
      throws IllegalArgumentException {
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

    builder.append(
        String.format("<svg width=\"%d\" height=\"%dpx\" xmlns=\"http://www.w3.org/2000/svg\">\n",
            model.getCanvasX() + model.getCanvasWidth(),
            model.getCanvasY() + model.getCanvasHeight()));

    for (ShapeViewModel shape : model.getShapes()) {
      switch (shape.getType()) {
        case RECTANGLE:
          builder
              .append(String.format("<rect id=\"%s\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" "
                      + "fill=\"rgb(%d, %d, %d)\" >\n", shape.getName(),
                  shape.getX(), shape.getY(),
                  shape.getWidth(),
                  shape.getHeight(), shape.getColor().getRed(), shape.getColor().getGreen(),
                  shape.getColor().getBlue()));

          for (MotionViewModel m : model.getMotionsOf(shape.getName())) {
            builder.append(this.makeAnimationRectangle(m));
          }

          builder.append("</rect>\n");
          break;
        case ELLIPSE:
          builder.append(String.format("<ellipse id=\"%s\" cx=\"%d\" cy=\"%d\" rx=\"%d\" ry=\"%d\" "
                  + "fill=\"rgb(%d, %d, %d)\" >\n", shape.getName(),
              shape.getX(), shape.getY(),
              shape.getWidth() / 2,
              shape.getHeight() / 2, shape.getColor().getRed(), shape.getColor().getGreen(),
              shape.getColor().getBlue()));

          for (MotionViewModel m : model.getMotionsOf(shape.getName())) {
            builder.append(this.makeAnimationEllipse(m));
          }

          builder.append("</ellipse>\n");
          break;
        case POLYGON:
          builder.append(String.format("<polygon id=\"%s\" points=\"", shape.getName()));
          List<Integer> points = DrawingPanelPolygon.getPoints(shape);
          for (int i = 0; i < points.size(); i = i + 2) {
            builder.append(String.format("%d,%d", points.get(i), points.get(i + 1)));
            if (i != points.size() - 1) {
              builder.append(" ");
            }
          }
          builder.append(String
              .format("\" fill=\"rgb(%d, %d, %d)\" />\n", shape.getColor().getRed(),
                  shape.getColor().getGreen(),
                  shape.getColor().getBlue()));
          break;
        default:
      }
    }

    builder.append("</svg>");

    return builder.toString();
  }

  private String makeAnimationRectangle(MotionViewModel m) {
    StringBuilder builder = new StringBuilder();
    for (MotionType type : m.getType()) {
      switch (type) {
        case MOVE:
          builder.append(String
              .format(
                  "<animate attributeType=\"xml\" begin=\"%.1f\" dur=\"%.1fms\" "
                      + "attributeName=\"x\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                  (m.getStartTick() / ticksPerSecond),
                  ((m.getEndTick() - m.getStartTick()) * 1000 / ticksPerSecond), m.getStartX(),
                  m.getEndX()));
          builder.append(String
              .format(
                  "<animate attributeType=\"xml\" begin=\"%.1f\" dur=\"%.1fms\" "
                      + "attributeName=\"y\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                  (m.getStartTick() / ticksPerSecond),
                  ((m.getEndTick() - m.getStartTick()) * 1000 / ticksPerSecond), m.getStartY(),
                  m.getEndY()));
          break;
        case SCALE:
          builder.append(String
              .format(
                  "<animate attributeType=\"xml\" begin=\"%.1f\" dur=\"%.1fms\" "
                      + "attributeName=\"width\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                  (m.getStartTick() / ticksPerSecond),
                  ((m.getEndTick() - m.getStartTick()) * 1000 / ticksPerSecond), m.getStartWidth(),
                  m.getEndWidth()));
          builder.append(String
              .format(
                  "<animate attributeType=\"xml\" begin=\"%.1f\" dur=\"%.1fms\" "
                      + "attributeName=\"height\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                  (m.getStartTick() / ticksPerSecond),
                  ((m.getEndTick() - m.getStartTick()) * 1000 / ticksPerSecond), m.getStartHeight(),
                  m.getEndHeight()));
          break;
        case COLOR:
          builder.append(String
              .format(
                  "<animate attributeType=\"xml\" begin=\"%.1f\" dur=\"%.1fms\" "
                      + "attributeName=\"fill\" from=\"rgb(%d,%d,%d)\" to=\"rgb(%d,%d,%d)\" "
                      + "fill=\"freeze\"/>\n",
                  (m.getStartTick() / ticksPerSecond),
                  ((m.getEndTick() - m.getStartTick()) * 1000 / ticksPerSecond),
                  m.getStartColor().getRed(), m.getStartColor().getGreen(),
                  m.getStartColor().getBlue(),
                  m.getEndColor().getRed(), m.getEndColor().getGreen(), m.getEndColor().getBlue()));
          break;
        default:
      }
    }

    return builder.toString();
  }

  private String makeAnimationEllipse(MotionViewModel m) {
    StringBuilder builder = new StringBuilder();
    for (MotionType type : m.getType()) {
      switch (type) {
        case MOVE:
          builder.append(String
              .format(
                  "<animate attributeType=\"xml\" begin=\"%.1f\" dur=\"%.1fms\" "
                      + "attributeName=\"cx\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                  (m.getStartTick() / ticksPerSecond),
                  ((m.getEndTick() - m.getStartTick()) * 1000 / ticksPerSecond), m.getStartX(),
                  m.getEndX()));
          builder.append(String
              .format(
                  "<animate attributeType=\"xml\" begin=\"%.1f\" dur=\"%.1fms\" "
                      + "attributeName=\"cy\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                  (m.getStartTick() / ticksPerSecond),
                  ((m.getEndTick() - m.getStartTick()) * 1000 / ticksPerSecond), m.getStartY(),
                  m.getEndY()));
          break;
        case SCALE:
          builder.append(String
              .format(
                  "<animate attributeType=\"xml\" begin=\"%.1f\" dur=\"%.1fms\" "
                      + "attributeName=\"rx\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                  (m.getStartTick() / ticksPerSecond),
                  ((m.getEndTick() - m.getStartTick()) * 1000 / ticksPerSecond),
                  m.getStartWidth() / 2,
                  m.getEndWidth() / 2));
          builder.append(String
              .format(
                  "<animate attributeType=\"xml\" begin=\"%.1f\" dur=\"%.1fms\" "
                      + "attributeName=\"ry\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                  (m.getStartTick() / ticksPerSecond),
                  ((m.getEndTick() - m.getStartTick()) * 1000 / ticksPerSecond),
                  m.getStartHeight() / 2,
                  m.getEndHeight() / 2));
          break;
        case COLOR:
          builder.append(String
              .format(
                  "<animate attributeType=\"xml\" begin=\"%.1f\" dur=\"%.1fms\" "
                      + "attributeName=\"fill\" from=\"rgb(%d,%d,%d)\" to=\"rgb(%d,%d,%d)\" "
                      + "fill=\"freeze\"/>\n",
                  (m.getStartTick() / ticksPerSecond),
                  ((m.getEndTick() - m.getStartTick()) * 1000 / ticksPerSecond),
                  m.getStartColor().getRed(), m.getStartColor().getGreen(),
                  m.getStartColor().getBlue(),
                  m.getEndColor().getRed(), m.getEndColor().getGreen(), m.getEndColor().getBlue()));
          break;
        default:
      }
    }

    return builder.toString();
  }

}
