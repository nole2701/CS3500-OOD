import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.motions.MotionModel;
import cs3500.animator.model.motions.MotionType;
import cs3500.animator.model.motions.implementations.BasicMotion;
import cs3500.animator.model.shapes.ShapeModel;
import cs3500.animator.model.shapes.ShapeType;
import cs3500.animator.model.shapes.ShapeViewModel;
import cs3500.animator.model.shapes.implementations.BasicShape;
import java.awt.Color;
import java.util.List;
import org.junit.Test;

/**
 * Represents tests for the motions.
 */
public class BasicMotionTest {

  ShapeModel r1;
  MotionModel everyMotion;
  MotionModel doNothingMotion;

  protected void initData() {
    this.r1 = new BasicShape("R1", 4, ShapeType.RECTANGLE, 0, 0,
        20, 10, Color.BLACK);

    this.everyMotion = new BasicMotion(r1, 1, 11, r1.getX(), 10, r1.getY(),
        10, 20, 40, 10, 20, r1.getColor(), Color.WHITE);

    this.doNothingMotion = new BasicMotion(r1, 11, 21, everyMotion);
  }

  @Test
  public void testGetType() {
    initData();
    List<MotionType> types = everyMotion.getType();
    assertTrue(types.contains(MotionType.COLOR));
    assertTrue(types.contains(MotionType.MOVE));
    assertTrue(types.contains(MotionType.SCALE));
    assertEquals(3, types.size());
  }

  @Test
  public void testGetShape() {
    initData();
    assertEquals(r1, everyMotion.getShape());
  }

  @Test
  public void testApplyDoNothing() {
    initData();
    ShapeModel r1Copy = new BasicShape("R1", 4, ShapeType.RECTANGLE, 0, 0,
        20, 10, Color.BLACK);
    doNothingMotion.apply(11);
    assertEquals(r1, r1Copy);
  }

  @Test
  public void testApply() {
    initData();
    ShapeModel r1Copy = new BasicShape("R1", 4, ShapeType.RECTANGLE, 0, 0,
        20, 10, Color.BLACK);
    assertEquals(r1, r1Copy);
    everyMotion.apply(11);
    ShapeViewModel ex = everyMotion.getShape();
    assertEquals(ex.getX(), 10);
    assertEquals(ex.getY(), 10);
    assertEquals(ex.getWidth(), 40);
    assertEquals(ex.getHeight(), 20);
    assertEquals(ex.getColor(), Color.WHITE);
  }

  @Test
  public void testToString() {
    initData();
    assertEquals(everyMotion.toString(),
        "motion R1   1   0   0  20  10   0   0   0      11  10  10  40  20 255 255 255");
  }

}
