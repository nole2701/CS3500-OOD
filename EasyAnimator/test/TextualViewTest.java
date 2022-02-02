import static org.junit.Assert.assertEquals;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.BasicAnimation;
import cs3500.animator.model.shapes.ShapeType;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.TextualView;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents tests for the Textual view.
 */
public class TextualViewTest {

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

    view = new TextualView(model, System.out, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTextualConstructorExceptionsModel() {
    view = new TextualView(null, System.out, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTextualConstructorExceptionsAppendable() {
    view = new TextualView(model, null, 10);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testTextualConstructorExceptionsTick() {
    view = new TextualView(model, System.out, -50);
  }

  @Test
  public void testToString() {
    this.setUp();
    assertEquals("canvas   0   0   5   5\n"
            + "shape R1 rectangle\n"
            + "shape R2 rectangle\n"
            + "motion R2 0.01 500 500  10  10   0   0   0     0.50 500 500 100 100 255   0   0\n"
            + "shape E1 ellipse\n"
            + "motion E1 0.20 250 250  29  29 217 217  38     0.30 250 500  39  39 197 197  58\n"
            + "shape Hex1 polygon\n"
            + "motion Hex1 0.01 750 750  10  10   0   0 255     0.50 750 750 100 100   0 255   0\n",
        view.toString());
  }
}