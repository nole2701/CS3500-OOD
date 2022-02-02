package cs3500.animator.view;

import cs3500.animator.model.AnimationViewModel;
import cs3500.animator.controller.ViewListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Represents the advanced visual view. This view is more advanced and is a GUI application. It has
 * various buttons and labels to make for an interactive experience.
 */
public class InteractiveView extends VisualView implements InteractiveAnimatorView,
    ActionListener {

  private final JButton loopButton;
  private final JLabel label;
  private final JTextField speedInput;
  private final List<ViewListener> listenerList;
  private boolean loop;

  /**
   * Constructs an interactive visual view.
   *
   * @param model to use to draw the animation and other things.
   */
  public InteractiveView(AnimationViewModel model) {
    super(model);

    listenerList = new ArrayList<>();
    loop = false;

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());

    JButton start = new JButton("Start");
    start.setActionCommand("Start");
    start.addActionListener(this);

    JButton pause = new JButton("Pause");
    pause.setActionCommand("Pause");
    pause.addActionListener(this);

    JButton resume = new JButton("Resume");
    resume.setActionCommand("Resume");
    resume.addActionListener(this);

    JButton restart = new JButton("Restart");
    restart.setActionCommand("Restart");
    restart.addActionListener(this);

    loopButton = new JButton("Loop: Off");
    loopButton.setActionCommand("Toggle Loop");
    loopButton.addActionListener(this);

    JButton setSpeed = new JButton("Set Speed");
    setSpeed.setActionCommand("Set Speed");
    setSpeed.addActionListener(this);

    label = new JLabel("Welcome to the interactive view! Click start to begin!");

    Font font1 = new Font("SansSerif", Font.BOLD, 15);
    speedInput = new JTextField("Set Speed Here");
    speedInput.setFont(font1);

    label.setFont(font1);

    buttonPanel.add(start);
    buttonPanel.add(pause);
    buttonPanel.add(resume);
    buttonPanel.add(restart);
    buttonPanel.add(loopButton);
    buttonPanel.add(setSpeed);
    buttonPanel.add(speedInput);

    add(label, BorderLayout.NORTH);
    add(buttonPanel, BorderLayout.PAGE_END);
  }


  @Override
  public void render() {
    setFocusable(true);
    requestFocus();
    setVisible(true);
  }


  @Override
  public void refresh() {
    repaint();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Start":
        this.label.setText("Clicked start: the animation is started!");
        emitStart();
        break;
      case "Pause":
        this.label.setText("Clicked pause: the animation is paused!");
        emitPause();
        break;
      case "Resume":
        this.label.setText("Clicked resume: the animation is resumed!");
        emitResume();
        break;
      case "Restart":
        this.label.setText("Clicked restart: the animation is restarted!");
        emitRestart();
        break;
      case "Toggle Loop":
        this.label.setText("Clicked loop: toggled the loop option!");
        this.loop = !this.loop;
        if (loopButton.getText().equals("Loop: Off")) {
          loopButton.setText("Loop: On");
        } else if (loopButton.getText().equals("Loop: On")) {
          loopButton.setText("Loop: Off");
        }
        emitLoopToggle();
        break;
      case "Set Speed":
        try {
          this.label
              .setText("Clicked set speed: the animation speed is set to: " + speedInput.getText());
          emitSetSpeed(Integer.parseInt(speedInput.getText()));
        } catch (NumberFormatException n) {
          this.label
              .setText("Clicked set speed: invalid integer for given.");
        }
        break;
      default:
    }
  }

  /**
   * Calls to the view listener to handle the start event.
   */
  private void emitStart() {
    for (ViewListener viewListener : listenerList) {
      viewListener.handleStart();
    }
  }

  /**
   * Calls to the view listener to handle the pause event.
   */
  private void emitPause() {
    for (ViewListener viewListener : listenerList) {
      viewListener.handlePause();
    }
  }

  /**
   * Calls to the view listener to handle the resume event.
   */
  private void emitResume() {
    for (ViewListener viewListener : listenerList) {
      viewListener.handleResume();
    }
  }

  /**
   * Calls to the view listener to handle the restart event.
   */
  private void emitRestart() {
    for (ViewListener viewListener : listenerList) {
      viewListener.handleRestart();
    }
  }

  /**
   * Calls to the view listener to handle the loop event.
   */
  private void emitLoopToggle() {
    for (ViewListener viewListener : listenerList) {
      viewListener.handleLoopToggle();
    }
  }

  /**
   * Calls to the view listener to handle the set speed event.
   *
   * @param tickRate speed for the view to be set at.
   */
  private void emitSetSpeed(int tickRate) {
    for (ViewListener viewListener : listenerList) {
      viewListener.handleSetSpeed(tickRate);
    }
  }

  @Override
  public void addListener(ViewListener listener) throws IllegalArgumentException {
    if (listener == null) {
      throw new IllegalArgumentException("The given listener is null.");
    }

    listenerList.add(listener);
  }
}
