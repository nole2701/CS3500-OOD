import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.BasicAnimation;
import cs3500.animator.model.motions.MotionModel;
import cs3500.animator.model.motions.MotionViewModel;
import cs3500.animator.model.motions.implementations.BasicMotion;
import cs3500.animator.model.shapes.ShapeModel;
import cs3500.animator.model.shapes.ShapeType;
import cs3500.animator.model.shapes.implementations.BasicShape;
import java.awt.Color;
import java.util.List;
import org.junit.Test;

/**
 * Represents tests for the animation model.
 */
public class BasicAnimationTest { 

  ShapeModel r1;
  ShapeModel r1At11;
  ShapeModel r2;
  ShapeModel r1Clone;
  MotionModel everyMotion;
  MotionModel moveMotion;
  MotionModel doNothingMotion;
  BasicAnimation a1;
  BasicAnimation a2;

  protected void initData() {
    this.r1 = new BasicShape("R1", 4, ShapeType.RECTANGLE, 0, 0,
        20, 10, Color.BLACK);
    this.r1At11 = new BasicShape("R1", 4, ShapeType.RECTANGLE, 10, 10,
        40, 20, Color.WHITE);
    this.r2 = new BasicShape("R2", 4, ShapeType.RECTANGLE, 20, 20,
        20, 10, Color.BLACK);
    this.r1Clone = new BasicShape("R1", 4, ShapeType.RECTANGLE, 0, 0,
        20, 10, Color.BLACK);

    this.everyMotion = new BasicMotion(r1, 1, 11, 0, 10, 0,
        10, 20, 40, 10, 20, Color.BLACK, Color.WHITE);

    this.moveMotion = new BasicMotion(r1, 11, 21, 10, 20, 10,
        20, 40, 40, 20, 20, Color.WHITE, Color.WHITE);

    this.doNothingMotion = new BasicMotion(r1, 11, 21, everyMotion);

    this.a1 = new BasicAnimation(0, 0, 500, 500);
    this.a2 = new BasicAnimation(0, 0, 500, 500);
    a1.addShape("R1", ShapeType.RECTANGLE, 4, 0, 0, 20, 10, Color.BLACK);
    a1.addShape("R2", ShapeType.RECTANGLE, 4, 20, 20, 20, 10, Color.BLACK);
    a1.addMotion("R1", 1, 11, 0, 10, 0, 10, 20,
        40, 10, 20, Color.BLACK, Color.WHITE);
    a1.addMotion("R1", 11, 21, 10, 20, 10, 20, 40,
        40, 20, 20, Color.WHITE, Color.WHITE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeSameName() {
    initData();
    a2.addShape("R1", ShapeType.RECTANGLE, 4, 0, 0, 20, 10, Color.BLACK);
    a2.addShape("R1", ShapeType.RECTANGLE, 4, 20, 20, 20, 10, Color.BLACK);
  }

  @Test
  public void testAddShape() {
    initData();
    a2.addShape("R1", ShapeType.RECTANGLE, 4, 0, 0, 20, 10, Color.BLACK);
    assertTrue(a2.getShapes().contains(r1));
  }

  @Test
  public void testRemoveShape() {
    initData();
    a2.addShape("R1", ShapeType.RECTANGLE, 4, 0, 0, 20, 10, Color.BLACK);
    assertTrue(a2.getShapes().contains(r1));
    a2.removeShape("R1");
    assertTrue(a2.getShapes().isEmpty());
  }

  @Test
  public void testAddMotion() {
    initData();
    a2.addShape("R1", ShapeType.RECTANGLE, 4, 0, 0, 20, 10, Color.BLACK);
    a2.addMotion("R1", 1, 11, 0, 10, 0, 10, 20,
        40, 10, 20, Color.BLACK, Color.WHITE);
    assertTrue(a2.getMotions().contains(everyMotion));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionNoShape() {
    initData();
    a2.addShape("R1", ShapeType.RECTANGLE, 4, 0, 0, 20, 10, Color.BLACK);
    a2.addMotion("R567", 1, 11, 0, 10, 0, 10, 20,
        40, 10, 20, Color.BLACK, Color.WHITE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionDuplicate() {
    initData();
    a2.addShape("R1", ShapeType.RECTANGLE, 4, 0, 0, 20, 10, Color.BLACK);
    a2.addMotion("R1", 1, 11, 0, 10, 0, 10, 20,
        40, 10, 20, Color.BLACK, Color.WHITE);
    a2.addMotion("R1", 1, 11, 0, 10, 0, 10, 20,
        40, 10, 20, Color.BLACK, Color.WHITE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionOverlap() {
    initData();
    a2.addShape("R1", ShapeType.RECTANGLE, 4, 0, 0, 20, 10, Color.BLACK);
    a2.addMotion("R1", 1, 11, 0, 10, 0, 10, 20,
        40, 10, 20, Color.BLACK, Color.WHITE);
    a2.addMotion("R1", 5, 21, 0, 10, 0, 10, 20,
        40, 10, 20, Color.BLACK, Color.WHITE);
  }

  @Test
  public void testAddMotionSort() {
    initData();
    a2.addShape("R1", ShapeType.RECTANGLE, 4, 0, 0, 20, 10, Color.BLACK);
    a2.addMotion("R1", 11, 21, 10, 20, 10, 20, 40,
        40, 20, 20, Color.WHITE, Color.WHITE);
    a2.addMotion("R1", 1, 11, 0, 10, 0, 10, 20,
        40, 10, 20, Color.BLACK, Color.WHITE);
    assertEquals(a2.getMotions().get(0), everyMotion);
    assertEquals(a2.getMotions().get(1), moveMotion);
  }

  @Test
  public void testRemoveMotion() {
    initData();
    a1.removeMotion("R1", 5);
    assertFalse(a1.getMotions().contains(everyMotion));
  }

  @Test
  public void testGetShape() {
    initData();
    assertEquals(a1.getShape("R1"), r1Clone);
  }

  @Test
  public void testGetShapeAtFrame() {
    initData();
    assertEquals(a1.getShapeAtFrame("R1", 11), r1At11);
  }

  @Test
  public void testGetShapes() {
    initData();
    assertTrue(a1.getShapes().contains(r1));
    assertTrue(a1.getShapes().contains(r2));
  }

  @Test
  public void testGetShapesAtCurrentFrame() {
    initData();
    assertTrue(a1.getShapesAtCurrentFrame(11).contains(r1At11));
  }

  @Test
  public void testGetMotions() {
    initData();
    List<MotionViewModel> motions = a1.getMotions();
    assertTrue(motions.contains(everyMotion));
    assertTrue(motions.contains(moveMotion));
  }

  @Test
  public void testGetMotionOf() {
    initData();
    assertEquals(a1.getMotionsOf("R1").size(), 2);
    assertTrue(a1.getMotionsOf("R2").isEmpty());
  }

  @Test
  public void testToString() {
    initData();
    assertEquals(a1.toString(), "shape R1 rectangle\n"
        + "motion R1   1   0   0  20  10   0   0   0      11  10  10  40  20 255 255 255\n"
        + "motion R1  11  10  10  40  20 255 255 255      21  20  20  40  20 255 255 255\n"
        + "shape R2 rectangle\n");
  }

}
