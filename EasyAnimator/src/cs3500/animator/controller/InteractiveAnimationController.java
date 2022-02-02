package cs3500.animator.controller;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.shapes.ShapeViewModel;
import cs3500.animator.view.InteractiveAnimatorView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Controller designed for the interactive view. Allows the GUI to be controlled by the client. The
 * controls are starting, resuming, pausing, restarting, as well as looping. The controller also
 * controls the speed of the animation.
 */
public class InteractiveAnimationController extends VisualAnimationController implements
    ViewListener {

  private boolean paused;
  private boolean loop;
  private boolean started;

  /**
   * Constructs a interactive animation controller. The action listener is initialized here to
   * override the action listener in the parent class.
   *
   * @param model    to control.
   * @param view     to control.
   * @param tickRate to calculate speed of animation.
   * @throws IllegalArgumentException if the given model or view are null.
   */
  public InteractiveAnimationController(AnimationModel model,
      InteractiveAnimatorView view, int tickRate) throws IllegalArgumentException {
    super(model, view, tickRate);

    this.paused = false;
    this.loop = false;
    this.started = false;
    view.addListener(this);

    this.actionListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (loop && tick >= model.getMotions().get(model.getMotions().size() - 1)
            .getEndTick()) {
          tick = 1;
        }

        for (ShapeViewModel shape : model.getShapesAtCurrentFrame(tick)) {
          view.drawShape(shape);
        }

        view.refresh();
        tick++;
      }
    };
  }

  @Override
  public void run() {
    if (this.started) {
      super.run();
    } else {
      this.renderView();
    }
  }

  @Override
  public void handleStart() {
    if (!this.started) {
      this.tick = 1;
      this.started = true;
      this.run();
    }
  }

  @Override
  public void handlePause() {
    if (!this.started) {
      return;
    }

    this.timer.stop();

    if (!paused) {
      for (ShapeViewModel shape : model.getShapesAtCurrentFrame(tick)) {
        view.drawShape(shape);
      }
      view.refresh();
    }

    this.paused = true;
  }

  @Override
  public void handleResume() {
    if (!this.started) {
      return;
    }

    if (this.paused) {
      this.paused = false;
      this.run();
    }
  }

  @Override
  public void handleRestart() {
    if (!this.started) {
      return;
    }

    this.tick = 1;
    this.run();
  }

  @Override
  public void handleLoopToggle() {
    loop = !loop;
  }

  @Override
  public void handleSetSpeed(int tickRate) throws IllegalArgumentException {

    if (tickRate <= 0) {
      JOptionPane.showMessageDialog(null, "Given tick speed must be positive!", "Error!",
          JOptionPane.INFORMATION_MESSAGE);
      return;
    }

    this.tickRate = tickRate;
    this.run();
  }
}
