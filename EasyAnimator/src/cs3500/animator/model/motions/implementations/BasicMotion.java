package cs3500.animator.model.motions.implementations;

import cs3500.animator.model.motions.MotionModel;
import cs3500.animator.model.motions.MotionType;
import cs3500.animator.model.motions.MotionViewModel;
import cs3500.animator.model.shapes.ShapeModel;
import cs3500.animator.model.shapes.ShapeViewModel;
import cs3500.animator.model.shapes.implementations.BasicShape;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Represents a basic motion. This motion can be a move, color, scale, or a combination of the
 * three.
 */
public class BasicMotion implements MotionModel {

  private final ShapeModel shape;
  private final int startTick;
  private final int endTick;
  private final int startX;
  private final int endX;
  private final int startY;
  private final int endY;
  private final int startWidth;
  private final int endWidth;
  private final int startHeight;
  private final int endHeight;
  private final Color startColor;
  private final Color endColor;

  /**
   * Constructs a basic motion.
   *
   * @param shape       to animate.
   * @param startTick   of the animation.
   * @param endTick     of the animation.
   * @param startX      of the shape.
   * @param endX        of the shape.
   * @param startY      of the shape.
   * @param endY        of the shape.
   * @param startWidth  of the shape.
   * @param endWidth    of the shape.
   * @param startHeight of the shape.
   * @param endHeight   of the shape.
   * @param startColor  of the shape.
   * @param endColor    of the shape.
   * @throws IllegalArgumentException if the given shape is null, if the startTick or endTick are
   *                                  less than 0, if the startTick is greater than the endTick, if
   *                                  the startWidth or endWidth are less than or equal to 0, if the
   *                                  startHeight or endHeight are less than or equal to 0, if the
   *                                  startColor or endColor are null.
   */
  public BasicMotion(ShapeViewModel shape, int startTick, int endTick, int startX, int endX,
      int startY,
      int endY, int startWidth, int endWidth, int startHeight, int endHeight,
      Color startColor, Color endColor) throws IllegalArgumentException {
    if (shape == null) {
      throw new IllegalArgumentException("The given shape must not be null.");
    }

    this.shape = new BasicShape(shape);

    if (startTick < 0) {
      throw new IllegalArgumentException("The given starting tick must not be negative.");
    }

    if (endTick < 0) {
      throw new IllegalArgumentException("The given starting tick must not be negative.");
    }

    if (startTick > endTick) {
      throw new IllegalArgumentException(
          "The motion must have a start tick that is less than the end tick");
    }

    this.startTick = startTick;
    this.endTick = endTick;

    this.startX = startX;
    this.endX = endX;

    this.startY = startY;
    this.endY = endY;

    if (startWidth <= 0 || endWidth <= 0) {
      throw new IllegalArgumentException("The start and end widths must be greater than 0.");
    }

    this.startWidth = startWidth;
    this.endWidth = endWidth;

    if (startHeight <= 0 || endHeight <= 0) {
      throw new IllegalArgumentException("The start and end heights must be greater than 0.");
    }

    this.startHeight = startHeight;
    this.endHeight = endHeight;

    if (startColor == null || endColor == null) {
      throw new IllegalArgumentException("The given start and end color must not be null.");
    }

    this.startColor = startColor;
    this.endColor = endColor;
  }

  /**
   * Constructs a basic motion that does nothing, based on a previous motion.
   *
   * @param shape      to animate.
   * @param startTick  of the animation.
   * @param endTick    of the animation.
   * @param prevMotion to start doing nothing from.
   */
  public BasicMotion(ShapeModel shape, int startTick, int endTick, MotionViewModel prevMotion) {
    this(shape, startTick, endTick, prevMotion.getEndX(), prevMotion.getEndX(),
        prevMotion.getEndY(), prevMotion.getEndY(),
        prevMotion.getEndWidth(), prevMotion.getEndWidth(), prevMotion.getEndHeight(),
        prevMotion.getEndHeight(), prevMotion.getEndColor(),
        prevMotion.getEndColor());
  }

  /**
   * Constructs a basic motion from another basic motion.
   *
   * @param motion to copy.
   */
  public BasicMotion(MotionViewModel motion) {
    this(motion.getShape(), motion.getStartTick(), motion.getEndTick(), motion.getStartX(),
        motion.getEndX(), motion.getStartY(), motion.getEndY(), motion.getStartWidth(),
        motion.getEndWidth(), motion.getStartHeight(), motion.getEndHeight(),
        motion.getStartColor(), motion.getEndColor());
  }

  @Override
  public void apply(int frame) {

    this.shape
        .teleport(this.startX, this.startY, this.startWidth, this.startHeight, this.startColor);

    int totalFrame = Math.max(this.endTick - this.startTick - 1, 1);
    int deltaX = (this.endX - this.startX) / totalFrame;
    int deltaY = (this.endY - this.startY) / totalFrame;
    int deltaWidth = (this.endWidth - this.startWidth) / totalFrame;
    int deltaHeight = (this.endHeight - this.startHeight) / totalFrame;
    int deltaRed = (this.endColor.getRed() - this.startColor.getRed()) / totalFrame;
    int deltaGreen = (this.endColor.getGreen() - this.startColor.getGreen()) / totalFrame;
    int deltaBlue = (this.endColor.getBlue() - this.startColor.getBlue()) / totalFrame;

    for (int i = this.startTick; i < frame; i++) {
      if (this.startTick >= this.endTick) {
        return;
      }

      for (MotionType type : MotionType.values()) {
        if (this.getType().contains(type)) {
          switch (type) {
            case MOVE: {
              this.shape.move(deltaX, deltaY);
              break;
            }
            case SCALE: {
              this.shape.scale(deltaWidth, deltaHeight);
              break;
            }
            case COLOR: {
              this.shape.color(deltaRed, deltaGreen, deltaBlue);
              break;
            }
            default:
              return;
          }
        }
      }
    }
  }


  @Override
  public ShapeViewModel getShape() {
    return new BasicShape(this.shape);
  }

  @Override
  public int getStartTick() {
    return this.startTick;
  }

  @Override
  public int getEndTick() {
    return this.endTick;
  }

  @Override
  public int getStartX() {
    return this.startX;
  }

  @Override
  public int getEndX() {
    return this.endX;
  }

  @Override
  public int getStartY() {
    return this.startY;
  }

  @Override
  public int getEndY() {
    return this.endY;
  }

  @Override
  public int getStartWidth() {
    return this.startWidth;
  }

  @Override
  public int getEndWidth() {
    return this.endWidth;
  }

  @Override
  public int getStartHeight() {
    return this.startHeight;
  }

  @Override
  public int getEndHeight() {
    return this.endHeight;
  }

  @Override
  public Color getStartColor() {
    return new Color(this.startColor.getRGB());
  }

  @Override
  public Color getEndColor() {
    return new Color(this.endColor.getRGB());
  }

  @Override
  public List<MotionType> getType() {
    List<MotionType> types = new ArrayList<>();

    if (this.startX != this.endX || this.startY != this.endY) {
      types.add(MotionType.MOVE);
    }

    if (this.startWidth != this.endWidth || this.startHeight != this.endHeight) {
      types.add(MotionType.SCALE);
    }

    if (!this.startColor.equals(this.endColor)) {
      types.add(MotionType.COLOR);
    }

    if (types.isEmpty()) {
      types.add(MotionType.EMPTY);
    }

    return types;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.shape.hashCode(), this.startTick, this.endTick, this.startX, this.endX,
        this.startY, this.endY, this.startWidth, this.endWidth, this.startHeight, this.endHeight,
        this.startColor.hashCode(), this.endColor.hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof BasicMotion)) {
      return false;
    }

    BasicMotion other = (BasicMotion) obj;

    return this.shape.equals(other.shape) && this.startTick == other.startTick
        && this.endTick == other.endTick && this.startX == other.startX && this.endX == other.endX
        && this.startY == other.startY && this.endY == other.endY
        && this.startWidth == other.startWidth && this.startHeight == other.startHeight
        && this.startColor.equals(other.startColor) && this.endColor.equals(other.endColor);
  }

  @Override
  public String toString() {

    return String
        .format("motion %s %3d %3d %3d %3d %3d %3d %3d %3d     %3d %3d %3d %3d %3d %3d %3d %3d",
            this.shape.getName(),
            this.startTick, this.startX, this.startY, this.startWidth, this.startHeight,
            this.startColor.getRed(), this.startColor.getGreen(), this.startColor.getBlue(),
            this.endTick, this.endX, this.endY, this.endWidth, this.endHeight,
            this.endColor.getRed(), this.endColor.getGreen(), this.endColor.getBlue());
  }

}
