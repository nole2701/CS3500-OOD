package cs3500.animator.view.panels;

import cs3500.animator.model.shapes.ShapeViewModel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.util.Objects;

/**
 * Represents a drawing panel for an ellipse.
 */
public class DrawingPanelEllipse extends Ellipse2D.Float implements IDrawingPanelShape {

  private final Color color;

  /**
   * Constructs a drawing panel for an ellipse.
   *
   * @param shape of the drawing panel, to create the ellipse from.
   */
  public DrawingPanelEllipse(ShapeViewModel shape) {
    super(shape.getX(),
        shape.getY(), shape.getWidth(), shape.getHeight());
    this.color = shape.getColor();
  }

  @Override
  public void draw(Graphics g) {
    Objects.requireNonNull(g);

    g.setColor(this.color);
    g.fillOval((int) x, (int) y, (int) width, (int) height);
  }
}
