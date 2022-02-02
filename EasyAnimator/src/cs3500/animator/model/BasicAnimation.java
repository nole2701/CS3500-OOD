package cs3500.animator.model;

import cs3500.animator.model.motions.MotionModel;
import cs3500.animator.model.motions.MotionViewModel;
import cs3500.animator.model.motions.implementations.BasicMotion;
import cs3500.animator.model.shapes.ShapeModel;
import cs3500.animator.model.shapes.ShapeType;
import cs3500.animator.model.shapes.ShapeViewModel;
import cs3500.animator.model.shapes.implementations.BasicShape;
import cs3500.animator.model.utils.SortMotions;
import cs3500.animator.util.AnimationBuilder;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Represents a basic animation. A basic animation has basic shapes and basic motions.
 */
public class BasicAnimation implements AnimationModel {

  private final Map<String, ShapeModel> shapes;
  private final List<MotionModel> motions;
  private final int x;
  private final int y;
  private final int width;
  private final int height;

  /**
   * Constructs an empty basic animation.
   */
  public BasicAnimation(int x, int y, int width, int height) {

    // TODO: Add illegalargument exception
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    this.shapes = new LinkedHashMap<>();
    this.motions = new ArrayList<>();
  }

  /**
   * Constructs a basic animation based on the given shapes and motions.
   *
   * @param shapes  to use.
   * @param motions to use.
   * @throws IllegalArgumentException if the map of shapes is null, or the map contains a null. if
   *                                  the list of motions is null, or the list contains a null.
   */
  public BasicAnimation(Map<String, ShapeModel> shapes, List<MotionModel> motions, int x, int y,
      int width, int height)
      throws IllegalArgumentException {
    this(x, y, width, height);

    if (shapes == null) {
      throw new IllegalArgumentException("The given shapes map must not be null.");
    }

    for (String name : shapes.keySet()) {
      if (shapes.get(name) == null) {
        throw new IllegalArgumentException("The given shape map must not contain a null.");
      }

      this.shapes.put(name, new BasicShape(shapes.get(name)));
    }

    if (motions == null) {
      throw new IllegalArgumentException("The given motions must not be null");
    }

    for (MotionModel motion : this.motions) {
      if (motion == null) {
        throw new IllegalArgumentException("The given motions list must not contain a null.");
      }

      this.motions.add(new BasicMotion(motion));
    }
  }

  @Override
  public void addShape(String name, ShapeType type, int vertices, int x, int y, int w, int h,
      Color color) throws IllegalArgumentException {

    if (this.shapes.containsKey(name)) {
      throw new IllegalArgumentException("There already exists a shape with the same name.");
    }

    this.shapes.put(name, new BasicShape(name, vertices, type, x, y, w, h, color));
  }

  @Override
  public void removeShape(String name) {
    this.shapes.remove(name);
  }

  @Override
  public void addMotion(String name, int startT, int endT, int startX, int endX, int startY,
      int endY, int startWidth, int endWidth, int startHeight, int endHeight, Color startColor,
      Color endColor) throws IllegalArgumentException {

    if (!this.shapes.containsKey(name)) {
      throw new IllegalArgumentException("There is no shape with the given name.");
    }

    ShapeModel targetShape = this.shapes.get(name);
    MotionModel newMotion = new BasicMotion(targetShape, startT, endT, startX, endX, startY, endY,
        startWidth, endWidth, startHeight, endHeight, startColor, endColor);

    // Check for duplicate motions or overlaps between types
    for (MotionModel motion : this.motions) {
      if (!motion.getShape().equals(newMotion.getShape())) {
        continue;
      }

      if (motion.equals(newMotion)) {
        throw new IllegalArgumentException(
            "There can't be duplicate motions for a shape." + "\n" + motion.toString() + "\n"
                + newMotion.toString());
      }

      if (this.overlaps(motion, newMotion)) {
        throw new IllegalArgumentException(
            "Motions must not overlap:"
                + "\n" + motion.toString() + "\n" + newMotion.toString());
      }

      // Make sure all values
      if (motion.getEndTick() == newMotion.getStartTick()) {
        if (motion.getEndX() != newMotion.getStartX()
            || motion.getEndY() != newMotion.getStartY()
            || motion.getEndWidth() != newMotion.getStartWidth()
            || motion.getEndHeight() != newMotion.getStartHeight()
            || !motion.getEndColor().equals(newMotion.getStartColor())) {
          throw new IllegalArgumentException(
              "Teleportation is not allowed:" + "\n" + motion.toString() + "\n" + newMotion
                  .toString());
        }
      }
    }

    this.motions.add(newMotion);
    this.motions.sort(new SortMotions());

  }

  @Override
  public void removeMotion(String name, int frame) {
    for (MotionModel motion : this.motions) {
      if (motion.getShape().getName().equals(name)) {
        if (frame >= motion.getStartTick() && frame < motion.getEndTick()) {
          this.motions.remove(motion);
        }
      }
    }
  }

  @Override
  public ShapeViewModel getShape(String name) {
    return this.shapes.get(name);
  }

  @Override
  public ShapeViewModel getShapeAtFrame(String name, int frame) {
    ShapeViewModel shape = this.shapes.get(name);

    for (MotionModel motion : this.motions) {
      if (motion.getShape().getName().equals(name)) {
        if (frame >= motion.getStartTick() && frame < motion.getEndTick()) {
          motion.apply(frame);
          shape = motion.getShape();
        }
      }
    }

    return shape;
  }

  @Override
  public List<ShapeViewModel> getShapes() {
    return new ArrayList<>(this.shapes.values());
  }

  @Override
  public List<ShapeViewModel> getShapesAtCurrentFrame(int frame) {

    List<ShapeViewModel> shapes = new ArrayList<>();

    for (MotionModel motion : this.motions) {
      if (frame >= motion.getStartTick() && frame < motion.getEndTick()) {
        motion.apply(frame);
        shapes.add(motion.getShape());
      }

    }

    List<ShapeViewModel> shapesCopy = new ArrayList<>();

    // Returning shapes in the order based on the linked hash map
    for (Entry<String, ShapeModel> entry : this.shapes.entrySet()) {
      for (ShapeViewModel s : shapes) {
        if (entry.getKey().equals(s.getName())) {
          shapesCopy.add(s);
        }
      }
    }

    return shapesCopy;
  }

  @Override
  public List<MotionViewModel> getMotions() {
    return new ArrayList<>(this.motions);
  }

  @Override
  public List<MotionViewModel> getMotionsOf(String name) {
    List<MotionViewModel> motions = new ArrayList<>();

    for (MotionViewModel motion : this.motions) {
      if (motion.getShape().getName().equals(name)) {
        motions.add(motion);
      }
    }

    return motions;
  }

  @Override
  public int getCanvasX() {
    return this.x;
  }

  @Override
  public int getCanvasY() {
    return this.y;
  }

  @Override
  public int getCanvasWidth() {
    return this.width;
  }

  @Override
  public int getCanvasHeight() {
    return this.height;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    for (ShapeViewModel shape : this.shapes.values()) {
      builder.append(String.format("shape %s %s\n", shape.getName(), shape.getType()));
      for (MotionViewModel motion : this.motions) {
        if (motion.getShape().equals(shape)) {
          builder.append(motion.toString()).append("\n");
        }
      }
    }

    return builder.toString();

  }

  /**
   * Checks if two motions are overlapping each other.
   *
   * @param m1 first motion.
   * @param m2 second motion.
   * @return true if the motions overlap, false otherwise.
   */
  private boolean overlaps(MotionViewModel m1, MotionViewModel m2) {
    if (m1 == null) {
      throw new IllegalArgumentException("The given m1 motion is null.");
    }

    if (m2 == null) {
      throw new IllegalArgumentException("The given m2 motion is null.");
    }

    return !(m1.getEndTick() <= m2.getStartTick() || m2.getEndTick() <= m1.getStartTick());
  }

  /**
   * Builder class use to adapt to other animations to this model.
   */
  public static final class Builder implements AnimationBuilder<AnimationModel> {

    private AnimationModel model;
    private final Map<String, String> shapes;


    /**
     * Constructs an empty animation model for the builder to add to.
     */
    public Builder() {
      this.model = new BasicAnimation(0, 0, 500, 500); // Default values
      this.shapes = new HashMap<>();
    }

    @Override
    public AnimationModel build() {
      return this.model;
    }

    @Override
    public AnimationBuilder<AnimationModel> setBounds(int x, int y, int width, int height) {
      this.model = new BasicAnimation(x, y, width, height);

      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> declareShape(String name, String type) {
      this.shapes.putIfAbsent(name, type);
      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> addMotion(String name, int t1, int x1, int y1, int w1,
        int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2,
        int b2) {

      if (shapes.containsKey(name)) {
        // Is this the first time we adding this shape?
        ShapeType type = getShapeType(this.shapes.get(name));
        int vertices = getNumVertices(this.shapes.get(name));

        try {
          this.model.addShape(name, type, vertices, x1, y1, w1, h1,
              new Color(r1, g1, b1));
        } catch (IllegalArgumentException ignored) {
        }
        this.model.addMotion(name, t1, t2, x1, x2, y1, y2, w1, w2, h1, h2, new Color(r1, g1, b1),
            new Color(r2, g2, b2));
      }

      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> addKeyframe(String name, int t, int x, int y, int w,
        int h, int r, int g, int b) {
      this.model
          .addMotion(name, t, t, x, x, y, y, w, w, h, h, new Color(r, g, b), new Color(r, g, b));
      return this;
    }

    /**
     * Gets the shape type based on the given string.
     *
     * @param type of the shape in string form.
     * @return the shape type.
     */
    private ShapeType getShapeType(String type) {
      switch (type) {
        case "rectangle":
          return ShapeType.RECTANGLE;
        case "ellipse":
          return ShapeType.ELLIPSE;
        case "triangle":
        case "pentagon":
        case "hexagon":
          return ShapeType.POLYGON;
        default:
          return null;
      }
    }

    /**
     * Gets the number of vertices of the a shape with the given string.
     *
     * @param type of the shape in string form.
     * @return the number of vertices as an integer.
     */
    private int getNumVertices(String type) {
      switch (type) {
        case "rectangle":
          return 4;
        case "ellipse":
          return 0;
        case "triangle":
          return 3;
        case "pentagon":
          return 5;
        case "hexagon":
          return 6;
        default:
          return -1;
      }

    }
  }
}
