package cs3500.animator.controller;

/**
 * Represents listeners for our views.
 */
public interface ViewListener {

  /**
   * Handles the start event. When the controller first runs, if this method is not called, the
   * animation does not start. If the animation is already started, nothing happens.
   */
  void handleStart();

  /**
   * Handles the pause event. If the animation is playing, this will pause it. If not, nothing will
   * happen.
   */
  void handlePause();

  /**
   * Handles the resume event. If the animation is paused, this will resume it. If not, nothing will
   * happen.
   */
  void handleResume();

  /**
   * Handles the restart event. If the animation is running, this will restart it from the
   * beginning.
   */
  void handleRestart();

  /**
   * Handles the loop event. This toggles the looping of the animation. If on, the animation will
   * loop. If not, the animation won't loop.
   */
  void handleLoopToggle();

  /**
   * Handles the set speed event. The new speed is set to the given tickRate.
   *
   * @param tickRate to set the speed of the animation to. Must be valid and positive.
   */
  void handleSetSpeed(int tickRate);
}
