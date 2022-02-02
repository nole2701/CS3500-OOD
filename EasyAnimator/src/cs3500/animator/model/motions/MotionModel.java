package cs3500.animator.model.motions;

/**
 * Represents a motion. Motions are used in animations to animate shapes. Motions are made in
 * tick/frame intervals.
 */
public interface MotionModel extends MotionViewModel {

  /**
   * Mutates the shape in the motion until it reaches the given frame.
   *
   * @param frame for motion.
   */
  void apply(int frame);

}
