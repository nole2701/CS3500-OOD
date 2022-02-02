package cs3500.animator.view;

import java.io.IOException;

/**
 * Represents a animator view. This animator view is for the most basic implementations. It allows
 * for the basic rendering of the animation.
 */
public interface AnimatorView {

  /**
   * Renders the animation view.
   *
   * @throws IOException if the view encounters an error during rendering.
   */
  void render() throws IOException;

}
