package cs3500.animator.model.shapes;

/**
 * Represents the type of shapes.
 */
public enum ShapeType {
  RECTANGLE, ELLIPSE, POLYGON;

  @Override
  public String toString() {
    switch (this) {
      case RECTANGLE:
        return "rectangle";
      case ELLIPSE:
        return "ellipse";
      case POLYGON:
        return "polygon";
      default:
        return "";
    }
  }


}
