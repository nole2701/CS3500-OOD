package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Card.Rank;
import cs3500.pyramidsolitaire.model.hw02.Card.Suit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class representing a Pyramid Solitaire with 3 Pyramids.
 */
public class MultiPyramidSolitaire extends BasicPyramidSolitaire {

  /**
   * Initialises the game.
   */
  public MultiPyramidSolitaire() {
    super();
  }


  /**
   * Makes a deck of 104 cards.
   * @return the deck.
   */
  @Override
  public List<Card> getDeck() {
    List<Card> result = new ArrayList<>();
    List<Card> result2 = new ArrayList<>();
    List<Suit> suitList = Arrays.asList(Suit.CLUBS, Suit.DIAMONDS,
        Suit.HEARTS, Suit.SPADES);

    for (int suitNum = 0 ; suitNum < 4 ; suitNum++) {
      for (int rankNum = 1 ; rankNum < 14 ; rankNum++) {
        result.add(new Card(Rank.valueOf(rankNum), suitList.get(suitNum)));
        result2.add(new Card(Rank.valueOf(rankNum), suitList.get(suitNum)));
      }
    }
    result.addAll(result2);
    return result;
  }

  @Override
  protected ArrayList<ArrayList<Card>> buildPyramid(List<Card> deck, int numRows) throws
      IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("Deck is null");
    } else if (numRows < 1) {
      throw new IllegalArgumentException("Number of rows must be positive");
    }
    ArrayList<ArrayList<Card>> result = new ArrayList<>();
    int topRowLength = 2 * (numRows / 2) + 1;
    int nullRows = (topRowLength / 2) - 1;

    for (int rowNum = 1 ; rowNum <= numRows ; rowNum++) {
      ArrayList<Card> row = new ArrayList<>();
      int targetSize;

      if (rowNum <= nullRows) {
        targetSize = (rowNum + topRowLength - 1) - (2 * (nullRows - (rowNum - 1)));
      } else {
        targetSize = (rowNum + topRowLength - 1);
      }

      for (int i = 1; i <= targetSize; i++) {
        row.add(deck.get(0));
        deck.remove(0);
      }

      if (rowNum <= nullRows) {
        addNulls(row, nullRows, rowNum);
      }

      result.add(row);
    }
    result.add((ArrayList<Card>)deck);
    return result;
  }

  /**
   * adds null "cards" into the row supplied to finalise the row.
   * @param row the row as a list.
   * @param nullRows how many rows of nulls there are.
   * @param rowNum row number of the current row
   */
  private void addNulls(ArrayList<Card> row, int nullRows, int rowNum) {
    //nullRows indexed 1, rowNum indexed 1
    ArrayList<Card> nulls = new ArrayList<>();
    for (int i = 1; i <= nullRows - (rowNum - 1); i++) {
      nulls.add(null);
    }
    row.addAll(rowNum, nulls);
    row.addAll(row.size() - rowNum, nulls);
  }

  @Override
  protected boolean checkDeck(List<Card> deck) throws IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("Deck is null");
    }
    boolean result = true;
    for (int i = 0 ; i < deck.size() ; i++) {
      int count = 0;
      for (Card temp : deck) {
        if (deck.get(i).equals(temp)) {
          count++ ;

        }
      }
      if (count != 2) {
        result = false;
        break;
      }
    }
    if (deck.size() < 104) {
      result = false;
    }
    return result;
  }

  @Override
  protected boolean checkNotEnoughCards(List<Card> deck, int numRows, int numDraw) throws
      IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("Deck is null");
    } else if (numRows < 1) {
      throw new IllegalArgumentException("Number of rows must be positive");
    } else if (numDraw < 0) {
      throw new IllegalArgumentException("Number of draw cards must not be negative");
    }
    int topRowLength = 2 * (numRows / 2) + 1;
    int nullRowLength = (topRowLength / 2) - 1;
    int totalPyramid = (numRows / 2) * ((2 * topRowLength) + numRows - 1);
    int nullPyramid = (nullRowLength / 2) * (2 + nullRowLength - 1);

    int cardsNeeded = totalPyramid - (2 * nullPyramid);
    return deck.size() < cardsNeeded;
  }








}
