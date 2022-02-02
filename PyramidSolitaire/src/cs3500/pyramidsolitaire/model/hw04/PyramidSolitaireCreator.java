package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.HashMap;
import java.util.Map;

/**
 * Uses the factory pattern to build a game of PyramidSolitaire using the create method.
 * Supports 3 types of games for each of the 3 enums.
 */
public class PyramidSolitaireCreator {


  /**
   * An enum for the 3 game types.
   */
  public enum GameType {
    BASIC("basic"),
    RELAXED("relaxed"),
    MULTIPYRAMID("multipyramid");


    private final String gameTypeValue;
    private static final Map<String, GameType> map = new HashMap<>();

    /**
     * Constructs the Suit class for Card.
     * @param suitValue String with the suit symbol.
     */
    GameType(String suitValue) {
      this.gameTypeValue = suitValue;
    }

    /**
     * Prints the suit symbol as a String.
     * @return a String for the suit symbol.
     */
    public String printGameType() {
      return this.gameTypeValue;
    }

    static {
      for (GameType gameTypeValue : GameType.values()) {
        map.put(gameTypeValue.printGameType(), gameTypeValue);
      }
    }

    public static GameType typeOf(String target) {
      return map.get(target);
    }


  }

  /**
   * Creates a game of PyramidSolitaire depending on the game type provided.
   * @param gametype the game type to create.
   * @return the game.
   */
  public static PyramidSolitaireModel<Card> create(GameType gametype) {
    if (gametype == null) {
      throw new IllegalArgumentException("GameType cannot be null");
    }
    PyramidSolitaireModel<Card> game = null;
    if (gametype == GameType.BASIC) {
      game = new BasicPyramidSolitaire();
    }
    if (gametype == GameType.RELAXED) {
      game = new RelaxedPyramidSolitaire();
    }
    if (gametype == GameType.MULTIPYRAMID) {
      game = new MultiPyramidSolitaire();
    }
    return game;
  }


}
