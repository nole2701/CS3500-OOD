import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
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
    PyramidSolitaireModel<Card> model = new BasicPyramidSolitaire();
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(
        new InputStreamReader(System.in), System.out);
    controller.playGame(model, model.getDeck(), true, 9, 5);
  }
}
