package cs3500.animator.view.panels;

import cs3500.animator.model.shapes.ShapeViewModel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * Represents a drawing panel.
 */
public class DrawingPanel extends JPanel {

  private final List<IDrawingPanelShape> shapes;

  /**
   * Constructs a drawing panel.
   */
  public DrawingPanel() {
    super();
    setBackground(Color.WHITE);
    this.shapes = new ArrayList<>();
  }

  /**
   * Paints a component on the panel, based on the given graphics.
   *
   * @param g the graphic.
   */
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    for (IDrawingPanelShape shape : shapes) {
      shape.draw(g);
    }

    shapes.clear();
  }

  /**
   * Adds a rectangle to the drawing panel shapes.
   *
   * @param shape the shape to draw as a rectangle.
   */
  public void addRectangle(ShapeViewModel shape) {
    shapes.add(new DrawingPanelRectangle(shape));
  }

  /**
   * Adds an ellipse to the drawing panel shapes.
   *
   * @param shape the shape to draw as an ellipse.
   */
  public void addEllipse(ShapeViewModel shape) {
    shapes.add(new DrawingPanelEllipse(shape));
  }

  /**
   * Adds a polygon to the drawing panel shapes.
   *
   * @param shape the shape to draw as a polygon.
   */
  public void addPolygon(ShapeViewModel shape) {
    shapes.add(new DrawingPanelPolygon(shape));
  }

}
