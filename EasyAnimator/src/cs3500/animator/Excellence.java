package cs3500.animator;

import cs3500.animator.controller.InteractiveAnimationController;
import cs3500.animator.controller.AnimationController;
import cs3500.animator.controller.BasicAnimationController;
import cs3500.animator.controller.VisualAnimationController;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.BasicAnimation;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.InteractiveView;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextualView;
import cs3500.animator.view.VisualAnimatorView;
import cs3500.animator.view.VisualView;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JOptionPane;

/**
 * Represents the animator.
 */
public class Excellence {

  /**
   * Entry point of the animator.
   *
   * @param args to process for the animation.
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      JOptionPane.showMessageDialog(null, "No arguments found!", "Error!",
          JOptionPane.INFORMATION_MESSAGE);
      return;
    }

    AnimationModel model = null;
    AnimatorView view;
    AnimationController controller = null;
    String viewType = "";
    OutputStream appendable = System.out;
    int tickPerSecond = 1;

    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-in":
          try {
            model = AnimationReader
                .parseFile(new FileReader(args[++i]), new BasicAnimation.Builder());
          } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found!", "Error!",
                JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
          }
          break;
        case "-view":
          viewType = args[++i];
          break;
        case "-out":
          try {
            String filePath = args[++i];
            new FileOutputStream(filePath).close();
            appendable = new FileOutputStream(filePath, true);
          } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Appendable error!", "Error!",
                JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
          }
          break;
        case "-speed":
          tickPerSecond = Integer.parseInt(args[++i]);
          break;
        default:
          JOptionPane.showMessageDialog(null, "Require at least -in and -view arguments.", "Error!",
              JOptionPane.INFORMATION_MESSAGE);
          return;
      }
    }

    if (model == null) {
      JOptionPane.showMessageDialog(null, "Model is null!", "Error!",
          JOptionPane.INFORMATION_MESSAGE);

      return;
    }

    switch (viewType) {
      case "text":
        view = new TextualView(model, appendable, tickPerSecond);
        controller = new BasicAnimationController(view);
        break;
      case "visual":
        view = new VisualView(model);
        controller = new VisualAnimationController(model, (VisualAnimatorView) view, tickPerSecond);
        break;
      case "interactive":
        view = new InteractiveView(model);
        controller = new InteractiveAnimationController(model, (InteractiveView) view,
            tickPerSecond);
        break;
      case "svg":
        view = new SVGView(model, appendable, tickPerSecond);
        controller = new BasicAnimationController(view);
        break;
      default:
        JOptionPane.showMessageDialog(null, "Could not create view!", "Error!",
            JOptionPane.INFORMATION_MESSAGE);
    }

    if (controller != null) {
      controller.run();
    } else {
      JOptionPane.showMessageDialog(null, "Controller was not initialized!", "Error!",
          JOptionPane.INFORMATION_MESSAGE);
    }


  }
}
