package cs3500.animator.view;

import cs3500.animator.model.AnimationViewModel;
import cs3500.animator.model.shapes.ShapeViewModel;
import cs3500.animator.view.panels.DrawingPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * Represents the visual view. This view is more advanced and is a GUI application.
 */
public class VisualView extends JFrame implements VisualAnimatorView {

  protected final DrawingPanel drawingPanel;

  /**
   * Constructs the visual view.
   */
  public VisualView(AnimationViewModel model) {
    super();

    if (model == null) {
      throw new IllegalArgumentException("The given model is null.");
    }

    setSize(new Dimension(model.getCanvasX() + model.getCanvasWidth(),
        model.getCanvasY() + model.getCanvasHeight()));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    drawingPanel = new DrawingPanel();
    drawingPanel.setPreferredSize(new Dimension(model.getCanvasX() + model.getCanvasWidth(),
        model.getCanvasY() + model.getCanvasHeight()));
    JScrollPane scrollPane = new JScrollPane(drawingPanel);
    setLayout(new BorderLayout());
    add(scrollPane, BorderLayout.CENTER);
  }


  @Override
  public void render() {
    setFocusable(true);
    requestFocus();
    setVisible(true);
  }

  @Override
  public void drawShape(ShapeViewModel shape) throws IllegalArgumentException {
    switch (shape.getType()) {
      case RECTANGLE:
        this.drawingPanel.addRectangle(shape);
        break;
      case ELLIPSE:
        this.drawingPanel.addEllipse(shape);
        break;
      case POLYGON:
        this.drawingPanel.addPolygon(shape);
        break;
      default:
        throw new IllegalArgumentException("Could not identify shape.");
    }
  }

  @Override
  public void refresh() {
    repaint();
  }

}
