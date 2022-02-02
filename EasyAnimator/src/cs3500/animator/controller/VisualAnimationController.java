package cs3500.animator.controller;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.shapes.ShapeViewModel;
import cs3500.animator.view.VisualAnimatorView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.Timer;

/**
 * Represents an animation controller for visual views. This controller is a bit more complex than
 * the basic controller.
 */
public class VisualAnimationController implements AnimationController {

  protected final AnimationModel model;
  protected final VisualAnimatorView view;
  protected Timer timer;
  protected int tickRate;
  protected int tick;
  protected ActionListener actionListener;

  /**
   * Constructs a visual animation controller.
   *
   * @param model    to control.
   * @param view     to control.
   * @param tickRate to calculate speed of animation.
   * @throws IllegalArgumentException if the given model or view are null.
   */
  public VisualAnimationController(AnimationModel model,
      VisualAnimatorView view, int tickRate) throws IllegalArgumentException {

    if (model == null) {
      throw new IllegalArgumentException("The given model must not be null.");
    }

    if (view == null) {
      throw new IllegalArgumentException("The given view must not be null.");
    }

    this.tickRate = tickRate;
    this.model = model;
    this.view = view;
    this.tick = 1;
    this.actionListener = new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        for (ShapeViewModel shape : model.getShapesAtCurrentFrame(tick)) {
          view.drawShape(shape);
        }

        view.refresh();
        tick++;
      }
    };

    timer = new Timer(1000 / this.tickRate, this.actionListener);
  }

  @Override
  public void run() {
    timer.stop();

    timer = new Timer(1000 / this.tickRate, this.actionListener);

    this.renderView();

    timer.start();
  }

  /**
   * Renders the view.
   */
  protected void renderView() {
    try {
      view.render();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
