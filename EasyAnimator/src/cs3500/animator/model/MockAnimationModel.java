package cs3500.animator.model;

import cs3500.animator.model.motions.MotionViewModel;
import cs3500.animator.model.shapes.ShapeType;
import cs3500.animator.model.shapes.ShapeViewModel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a mock of the animation model.
 */
public class MockAnimationModel implements AnimationModel {

  public final StringBuilder log;

  /**
   * Constructs a mock model.
   *
   * @param log to keep track of output.
   */
  public MockAnimationModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void addShape(String name, ShapeType type, int vertices, int x, int y, int w, int h,
      Color color) throws IllegalArgumentException {
    this.log.append(String.format("Add shape:\n"
            + "name: %s\n"
            + "type: %s\n"
            + "vertices: %d\n"
            + "x: %d\n"
            + "y: %d\n"
            + "w: %d\n"
            + "h: %d\n"
            + "rgb:(%d, %d, %d)\n", name, type, vertices,
        x, y, w, h, color.getRed(), color.getGreen(), color.getGreen()));
  }

  @Override
  public void removeShape(String name) {
    this.log.append("Remove shape ").append(name).append("\n");
  }

  @Override
  public void addMotion(String name, int startT, int endT, int startX, int endX, int startY,
      int endY, int startWidth, int endWidth, int startHeight, int endHeight, Color startColor,
      Color endColor) throws IllegalArgumentException {
    this.log.append(String.format("Add motion:\n"
            + "name: %s\n"
            + "start tick: %d\n"
            + "end tick: %d\n"
            + "start x: %d\n"
            + "end x: %d\n"
            + "start y: %d\n"
            + "end y: %d\n"
            + "start width: %d\n"
            + "end width: %d\n"
            + "start height: %d\n"
            + "end height: %d\n"
            + "start color: rgb(%d, %d, %d)\n"
            + "end color: rgb(%d, %d, %d)\n", name, startT, endT, startX, endX, startY, endY,
        startWidth, endWidth, startHeight, endHeight, startColor.getRed(), startColor.getGreen(),
        startColor.getBlue(), endColor.getRed(), endColor.getGreen(), endColor.getBlue()));
  }

  @Override
  public void removeMotion(String name, int frame) {
    this.log.append("Remove motion for shape ").append(name).append(" at frame: ").append(frame)
        .append(".\n");
  }

  @Override
  public ShapeViewModel getShape(String name) {
    this.log.append("Get the shape with the name: ").append(name).append("\n");
    return null;
  }

  @Override
  public ShapeViewModel getShapeAtFrame(String name, int frame) {
    this.log.append("Get the shape with the name: ").append(name).append("at the frame: ")
        .append(frame).append(".\n");
    return null;
  }

  @Override
  public List<ShapeViewModel> getShapes() {
    this.log.append("Get all shapes in the model.");
    return new ArrayList<>();
  }

  @Override
  public List<ShapeViewModel> getShapesAtCurrentFrame(int frame) {
    this.log.append("Get all shapes at the frame: ").append(frame).append(".\n");
    return new ArrayList<>();
  }

  @Override
  public List<MotionViewModel> getMotions() {
    this.log.append("Get all motions in the model.");
    return new ArrayList<>();
  }

  @Override
  public List<MotionViewModel> getMotionsOf(String name) {
    this.log.append("Get all motions of the shape named: ").append(name).append("\n");
    return new ArrayList<>();
  }

  @Override
  public int getCanvasX() {
    this.log.append("Get the leftmost x.");
    return 0;
  }

  @Override
  public int getCanvasY() {
    this.log.append("Get the topmost y.");
    return 0;
  }

  @Override
  public int getCanvasWidth() {
    this.log.append("Get the canvas width.");
    return 0;
  }

  @Override
  public int getCanvasHeight() {
    this.log.append("Get the canvas height.");
    return 0;
  }
}
