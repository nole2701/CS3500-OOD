package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class representing a RELAXED RULES model of Pyramid Solitaire.
 */
public class RelaxedPyramidSolitaire extends BasicPyramidSolitaire {

  /**
   * Initialises the game.
   */
  public RelaxedPyramidSolitaire() {
    super();
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {
    if (!super.gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    if ((!canBeRemoved(row1, card1) || !canBeRemoved(row2, card2))
        && !relaxedException(row1, card1, row2, card2)) {
      throw new IllegalArgumentException("Cards cannot be removed yet.");
    }
    Card c1 = super.pyramid.get(row1).get(card1);
    Card c2 = super.pyramid.get(row2).get(card2);
    if (c1.getRank() + c2.getRank() != 13) {
      throw new IllegalArgumentException("Cards don't add up to 13.");
    } else if (c1.getRank() + c2.getRank() == 13 || relaxedException(row1, card1, row2, card2)) {
      super.pyramid.get(row1).set(card1, null);
      super.pyramid.get(row2).set(card2, null);
    }
  }

  /**
   * Checks if two cards classify as a relaxed exception for a remove 2.
   * @param row1 row of first card.
   * @param card1 column of first card.
   * @param row2 row of second card.
   * @param card2 column of second card.
   * @return true if they can be removed under relaxed rules.
   */
  private boolean relaxedException(int row1, int card1, int row2, int card2) {
    boolean canDo = true;
    int upperRow = -1234;
    int upperCard = -1234;
    int lowerRow = -1234;
    int lowerCard = -1234;
    if (row1 > row2) {
      upperRow = row1;
      upperCard = card1;
      lowerRow = row2;
      lowerCard = card2;
    } else if (row2 > row1) {
      upperRow = row2;
      upperCard = card2;
      lowerRow = row1;
      lowerCard = card1;
    }

    //Must be adjacent row.
    if (upperRow - lowerRow != 1) {
      canDo = false;
    }

    //Must be directly on top.
    int cardDiff = upperCard - lowerCard;
    if (cardDiff != 1 && cardDiff != 0) {
      canDo = false;
    }

    //Other card on top has to be null
    Card otherCard = null;
    if (cardDiff == 0) {
      otherCard = super.pyramid.get(upperRow).get(upperCard + 1);
    } else if (cardDiff == 1) {
      otherCard = super.pyramid.get(upperRow).get(upperCard - 1);
    }
    if (otherCard != null) {
      canDo = false;
    }

    if (super.pyramid.get(row1).get(card1) == null || super.pyramid.get(row2).get(card2) == null) {
      canDo = false;
    } else if (super.pyramid.get(row1).get(card1).getRank() +
        super.pyramid.get(row2).get(card2).getRank() != 13) {
      canDo = false;
    }
    //If the move is possible, canDo should still be true
    return canDo;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    boolean result = true;

    ArrayList<Card> accessibleCards = new ArrayList<>(draw);
    for (int i = 0 ; i < pyramid.size(); i++) {

      for (int k = 0 ; k < pyramid.get(i).size(); k++) {
        if (canBeRemoved(i, k)) {
          accessibleCards.add(pyramid.get(i).get(k));
        }


        //Checks for relaxed exceptions
        if (i < pyramid.size() - 1) {
          for (int n = 0; n < pyramid.get(i + 1).size(); n++) {
            if (relaxedException(i, k, i + 1, n)) {
              accessibleCards.add(pyramid.get(i).get(k));
            }
          }
        }


      }
    }

    accessibleCards.removeAll(Collections.singleton(null));

    for (Card accessibleCard : accessibleCards) {
      if (accessibleCard.getRank() == 13) {
        result = false;
        break;
      }
    }
    for (int i = 0 ; i < accessibleCards.size() - 1; i++) {

      for (int j = i + 1 ; j < accessibleCards.size() ; j++) {
        if (accessibleCards.get(i).getRank() + (accessibleCards.get(j)).getRank() == 13) {
          result = false;
          break;
        }
      }

    }
    if (stock.size() > 0 && getNumDraw() > 0) {
      result = false;
    }

    if (getScoreHelp() == 0) {
      result = true;
    }

    return result;
  }

}
