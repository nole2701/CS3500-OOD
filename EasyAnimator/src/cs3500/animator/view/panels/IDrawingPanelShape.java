package cs3500.animator.view.panels;

import java.awt.Graphics;

/**
 * Represents all drawing panel shapes.
 */
public interface IDrawingPanelShape {

  /**
   * Draws the given graphic onto the drawing panel.
   *
   * @param g to draw.
   */
  void draw(Graphics g);

}
