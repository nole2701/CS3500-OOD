package cs3500.animator.controller;

import cs3500.animator.view.AnimatorView;
import java.io.IOException;

/**
 * Represents a basic animation controller. This controller is useful for animation views that are
 * simple. For example, the textual and svh views can use this to render.
 */
public class BasicAnimationController implements AnimationController {

  private final AnimatorView view;

  /**
   * Constructs a basic animation controller.
   *
   * @param view for the controller to display.
   * @throws IllegalArgumentException if the given view is null.
   */
  public BasicAnimationController(AnimatorView view) throws IllegalArgumentException {

    if (view == null) {
      throw new IllegalArgumentException("The given view must not be null.");
    }

    this.view = view;
  }


  @Override
  public void run() {
    try {
      view.render();
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not render view.");
    }
  }
}
