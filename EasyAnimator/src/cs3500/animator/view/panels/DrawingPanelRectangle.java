package cs3500.animator.view.panels;

import cs3500.animator.model.shapes.ShapeViewModel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

/**
 * Represents a drawing panel for a rectangle.
 */
public class DrawingPanelRectangle extends Rectangle2D.Float implements IDrawingPanelShape {

  private final Color color;

  /**
   * Constructs a drawing panel for a rectangle.
   *
   * @param shape of the drawing panel, to create the rectangle from.
   */
  public DrawingPanelRectangle(ShapeViewModel shape) {
    super(shape.getX(),
        shape.getY(), shape.getWidth(), shape.getHeight());
    this.color = shape.getColor();
  }


  @Override
  public void draw(Graphics g) {
    Objects.requireNonNull(g);

    g.setColor(this.color);
    g.fillRect((int) x, (int) y, (int) width, (int) height);
  }
}