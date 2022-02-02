package cs3500.animator.view.panels;

import cs3500.animator.model.shapes.ShapeViewModel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a drawing panel for a polygon.
 */
public class DrawingPanelPolygon extends Polygon implements IDrawingPanelShape {

  private final Color color;

  /**
   * Constructs a drawing panel for a polygon.
   *
   * @param shape of the drawing panel, to create the polygon from.
   */
  public DrawingPanelPolygon(ShapeViewModel shape) {
    super();
    this.color = shape.getColor();
    List<Integer> points = getPoints(shape);

    for (int i = 0; i < points.size(); i = i + 2) {
      this.addPoint(points.get(i), points.get(i + 1));
    }
  }

  /**
   * Returns the points from the given shape. Use in constructing polygons.
   *
   * @param shape to generate polygon points from.
   * @return the points.
   */
  public static List<Integer> getPoints(ShapeViewModel shape) {
    double interiorAngle = Math.toRadians(360 / (double) shape.getVertices());
    int centerX = shape.getX();
    int centerY = shape.getY();
    double radius = shape.getWidth();
    int sides = shape.getVertices();

    // The given width is the radius of the circumscribing circle.
    // Height does not affect the size.

    List<Integer> points = new ArrayList<>();
    for (int i = 0; i < sides; i++) {
      points.add(centerX - (int) (radius * Math.sin(interiorAngle * i)));
      points.add(centerY - (int) (radius * Math.cos(interiorAngle * i)));
    }
    return points;
  }


  @Override
  public void draw(Graphics g) {
    Objects.requireNonNull(g);

    g.setColor(this.color);
    g.fillPolygon(this);
  }
}