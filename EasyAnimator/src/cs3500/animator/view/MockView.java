package cs3500.animator.view;

import cs3500.animator.controller.ViewListener;
import cs3500.animator.model.shapes.ShapeViewModel;
import java.io.IOException;

/**
 * Mock view that works for all types of views.
 */
public class MockView implements InteractiveAnimatorView {

  public final StringBuilder log;

  /**
   * Constructs a mock view.
   *
   * @param log to keep track of output.
   */
  public MockView(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void addListener(ViewListener listener) throws IllegalArgumentException {
    this.log.append("Added a listener to the view.\n");
  }

  @Override
  public void render() throws IOException {
    this.log.append("Render view.\n");
  }

  @Override
  public void drawShape(ShapeViewModel shape) throws IllegalArgumentException {
    this.log.append("Draw shape: ").append(shape.toString()).append("\n");
  }

  @Override
  public void refresh() {
    this.log.append("Refresh view.\n");
  }
}
