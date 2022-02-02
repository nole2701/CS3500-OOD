package cs3500.pyramidsolitaire;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import java.io.InputStreamReader;

/**
 * Main class for playing the game.
 */
public class Main {



  /**
   * Main method for playing the game.
   * @param args Main method.
   */
  public static void main(String[] args) {
    String gameType = args[0];
    int rows = 7;
    int draw = 3;

    try {
      rows = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      System.err.println("Argument" + args[1] + " must be an integer.");
      System.exit(1);
    }

    try {
      draw = Integer.parseInt(args[2]);
    } catch (NumberFormatException e) {
      System.err.println("Argument" + args[2] + " must be an integer.");
      System.exit(1);
    }

    PyramidSolitaireModel<Card> model = PyramidSolitaireCreator.create(GameType.typeOf(gameType));
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(
        new InputStreamReader(System.in), System.out);

    try {
      controller.playGame(model, model.getDeck(), true, rows, draw);
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
    }




  }
}
