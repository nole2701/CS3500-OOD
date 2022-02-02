package cs3500.animator.model.utils;

import cs3500.animator.model.motions.MotionViewModel;
import java.util.Comparator;

/**
 * Represents a util class to sort motions.
 */
public class SortMotions implements Comparator<MotionViewModel> {

  @Override
  public int compare(MotionViewModel o1, MotionViewModel o2) {

    return Integer.compare(o1.getStartTick(), o2.getStartTick());
  }
}
