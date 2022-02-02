package cs3500.animator.view;

import cs3500.animator.controller.ViewListener;

/**
 * Advanced visual view representation. This extends the normal visual view but adds listeners.
 */
public interface InteractiveAnimatorView extends VisualAnimatorView {

  /**
   * Adds a listener to this view. Listeners are used to listen for events and reflect them on the
   * view.
   *
   * @param listener to add to the view.
   */
  void addListener(ViewListener listener) throws IllegalArgumentException;
}
