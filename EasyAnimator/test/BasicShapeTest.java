import static org.junit.Assert.assertEquals;

import cs3500.animator.model.shapes.ShapeModel;
import cs3500.animator.model.shapes.ShapeType;
import cs3500.animator.model.shapes.ShapeViewModel;
import cs3500.animator.model.shapes.implementations.BasicShape;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents tests for the shapes.
 */
public class BasicShapeTest {

  ShapeModel r1;
  ShapeModel r2;
  ShapeViewModel r1View;
  ShapeViewModel r2View;

  ShapeModel e1;
  ShapeModel e2;
  ShapeViewModel e1View;
  ShapeViewModel e2View;

  ShapeModel p1;
  ShapeModel p2;
  ShapeViewModel p1View;
  ShapeViewModel p2View;

  @Before
  public void init() {
    this.r1 = new BasicShape("R1", 4, ShapeType.RECTANGLE, 0, 0, 7, 2, Color.BLUE);
    this.r2 = new BasicShape("R2", 4, ShapeType.RECTANGLE, 100, 20, 4, 4, Color.YELLOW);

    this.e1 = new BasicShape("E1", 0, ShapeType.ELLIPSE, -10, -20, 6, 8, Color.RED);
    this.e2 = new BasicShape("E2", 0, ShapeType.ELLIPSE, -100, 20, 3, 3, Color.YELLOW);

    this.p1 = new BasicShape("P1", 3, ShapeType.POLYGON, 25, 100, 5, 5, Color.BLACK);
    this.p2 = new BasicShape("P2", 6, ShapeType.POLYGON, 90, -24, 10, 4, Color.CYAN);

    r1View = r1;
    r2View = r2;

    e1View = e1;
    e2View = e2;

    p1View = p1;
    p2View = p2;
  }

  // TODO: Add test exceptions for creating shapes

  @Test
  public void testMoveShape() {
    // Test moving a rectangular shape:
    assertEquals(0, r1View.getX());
    assertEquals(0, r1View.getY());
    r1.move(10, 0);
    assertEquals(10, r1View.getX());
    assertEquals(0, r1View.getY());
    r1.move(0, 200);
    assertEquals(10, r1View.getX());
    assertEquals(200, r1View.getY());

    // Test moving an ellipse shape:
    assertEquals(-10, e1View.getX());
    assertEquals(-20, e1View.getY());
    e1.move(100, 5);
    assertEquals(90, e1View.getX());
    assertEquals(-15, e1View.getY());
    e1.move(-15, 0);
    assertEquals(75, e1View.getX());
    assertEquals(-15, e1View.getY());

    // Test moving a polygon shape:
    assertEquals(90, p2View.getX());
    assertEquals(-24, p2View.getY());
    p2.move(100, 5);
    assertEquals(190, p2View.getX());
    assertEquals(-19, p2View.getY());
    p2.move(-10, 20);
    assertEquals(180, p2View.getX());
    assertEquals(1, p2View.getY());
  }

  @Test
  public void testScaleShape() {
    // Test scaling a rectangular shape:
    assertEquals(4, r2View.getWidth());
    assertEquals(4, r2View.getHeight());
    r2.scale(7, 0);
    assertEquals(11, r2View.getWidth());
    assertEquals(4, r2View.getHeight());

    // Test scaling an ellipse shape:
    assertEquals(3, e2View.getWidth());
    assertEquals(3, e2View.getHeight());
    e2.scale(0, -10);
    assertEquals(3, e2View.getWidth());
    assertEquals(1, e2View.getHeight());

    // Test scaling a polygons shape:
    assertEquals(5, p1View.getWidth());
    assertEquals(5, p1View.getHeight());
    p1.scale(-20, -10);
    assertEquals(1, p1View.getWidth());
    assertEquals(1, p1View.getHeight());
  }

  @Test
  public void testColorShape() {
    // Test scaling a rectangular shape:
    assertEquals(Color.YELLOW, r2View.getColor());
    r2.color(0, -20, 25);
    assertEquals(new Color(255, 235, 25), r2View.getColor());

    // Test scaling an ellipse shape:
    // TODO: Add tests

    // Test scaling a polygons shape:
    // TODO: Add tests
  }

  @Test
  public void testToStringOfShapes() {
    assertEquals("shape R1 rectangle", r1View.toString());
    assertEquals("shape R2 rectangle", r2View.toString());
    assertEquals("shape E1 ellipse", e1View.toString());
    assertEquals("shape E2 ellipse", e2View.toString());
    assertEquals("shape P1 polygon", p1View.toString());
    assertEquals("shape P2 polygon", p2View.toString());
  }

}