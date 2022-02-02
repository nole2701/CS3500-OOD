import static org.junit.Assert.assertEquals;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.BasicAnimation;
import cs3500.animator.model.shapes.ShapeType;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.SVGView;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents tests for the SVG view.
 */
public class SVGViewTest {

  AnimationModel model;
  AnimatorView view;

  @Before
  public void setUp() {
    model = new BasicAnimation(0, 0, 500, 500);

    model.addShape("R1", ShapeType.RECTANGLE, 4, 0, 0, 70, 20, Color.BLACK);
    model.addShape("R2", ShapeType.RECTANGLE, 4, 100, 200, 40, 40, Color.YELLOW);
    model.addShape("E1", ShapeType.ELLIPSE, 0, 100, 200, 60, 80, Color.GRAY);
    model.addShape("Hex1", ShapeType.POLYGON, 6, 0, 0, 50, 1, Color.RED);

    model.addMotion("R2", 1, 50, 500, 500, 500, 500, 10, 100, 10, 100, Color.BLACK, Color.RED);
    model.addMotion("E1", 1, 50, 250, 250, 250, 250, 10, 100, 10, 100, Color.YELLOW, Color.GRAY);
    model.addMotion("E1", 20, 30, model.getShapeAtFrame("E1", 20).getX(),
        model.getShapeAtFrame("E1", 30).getX(), model.getShapeAtFrame("E1", 20).getY(),
        model.getShapeAtFrame("E1", 30).getY() + 250, model.getShapeAtFrame("E1", 20).getWidth(),
        model.getShapeAtFrame("E1", 30).getWidth(), model.getShapeAtFrame("E1", 20).getHeight(),
        model.getShapeAtFrame("E1", 30).getHeight(), model.getShapeAtFrame("E1", 20).getColor(),
        model.getShapeAtFrame("E1", 30).getColor());
    model.addMotion("Hex1", 1, 50, 750, 750, 750, 750, 10, 100, 10, 100, Color.BLUE, Color.GREEN);

    view = new SVGView(model, System.out, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSVGConstructorExceptionsModel() {
    view = new SVGView(null, System.out, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSVGConstructorExceptionsAppendable() {
    view = new SVGView(model, null, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSVGConstructorExceptionsTick() {
    view = new SVGView(model, System.out, -50);
  }

  @Test
  public void testToString() {
    this.setUp();
    assertEquals("<?xml version=\"1.0\" encoding=\"utf\"8?>\n"
        + "<!DOCTYPE PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \n"
        + "  \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n"
        + "<svg width=\"5\" height=\"5px\" xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "  <rect x=\"0\" y=\"0\" width=\"70\" height=\"20\"\n"
        + "        fill=\"rgb(0, 0, 0)\" />\n"
        + "  <rect x=\"100\" y=\"200\" width=\"40\" height=\"40\"\n"
        + "        fill=\"rgb(255, 255, 0)\" />\n"
        + "  <ellipse cx=\"100\" cy=\"200\" rx=\"30\" ry=\"40\"\n"
        + "        fill=\"rgb(128, 128, 128)\" />\n"
        + "  <polygon points=\"0,-50 -43,-25 -43,24 0,50 43,25 43,-24 \"\n"
        + "        fill=\"rgb(255, 0, 0)\" />\n"
        + "</svg>", view.toString());
  }
}