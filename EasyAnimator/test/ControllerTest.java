import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.InteractiveAnimationController;
import cs3500.animator.controller.BasicAnimationController;
import cs3500.animator.model.MockAnimationModel;
import cs3500.animator.view.MockView;
import org.junit.Test;

/**
 * Tests for controller.
 */
public class ControllerTest {

  InteractiveAnimationController controller1;
  BasicAnimationController controller2;
  MockAnimationModel model1;
  MockView view1;
  MockView view2;


  protected void initData() {
    model1 = new MockAnimationModel(new StringBuilder());
    view1 = new MockView(new StringBuilder());
    view2 = new MockView(new StringBuilder());
    controller1 = new InteractiveAnimationController(model1, view1, 10);
    controller2 = new BasicAnimationController(view2);
  }

  @Test
  public void testAddListener() {
    initData();
    assertEquals(view1.log.toString(), "Added a listener to the view.\n");
  }

  @Test
  public void testRun() {
    initData();
    controller1.run();
    assertEquals(view1.log.toString(), "Added a listener to the view.\n"
        + "Render view.\n");

  }

  @Test
  public void testHandleStart() {
    initData();
    controller1.run();
    controller1.handleStart();
    assertEquals(view1.log.toString(), "Added a listener to the view.\n"
        + "Render view.\n"
        + "Render view.\n");
  }

  @Test
  public void testHandlePause() {
    initData();
    controller1.run();
    controller1.handlePause();
    assertEquals(view1.log.toString(), "Added a listener to the view.\n"
        + "Render view.\n");
  }

  @Test
  public void testHandleResume() {
    initData();
    controller1.run();
    controller1.handleResume();
    assertEquals(view1.log.toString(), "Added a listener to the view.\n"
        + "Render view.\n");

  }

  @Test
  public void testHandleRestart() {
    initData();
    controller1.run();
    controller1.handleRestart();
    assertEquals(view1.log.toString(), "Added a listener to the view.\n"
        + "Render view.\n");
  }

  @Test
  public void testHandleLoopToggle() {
    initData();
    controller1.run();
    controller1.handleLoopToggle();
    assertEquals(view1.log.toString(), "Added a listener to the view.\n"
        + "Render view.\n");
  }

  @Test
  public void testHandleSetSpeed() {
    initData();
    controller1.run();
    controller1.handleSetSpeed(10);
    assertEquals(view1.log.toString(), "Added a listener to the view.\n"
        + "Render view.\n"
        + "Render view.\n");
  }

  @Test
  public void testModelRun() {
    initData();
    controller1.run();
    assertEquals(model1.log.toString(), "");
  }

  @Test
  public void testBasicRun() {
    initData();
    controller2.run();
    assertEquals(view2.log.toString(), "Render view.\n");
  }



}
