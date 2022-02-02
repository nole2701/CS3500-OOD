package cs3500.pyramidsolitaire.model.hw02;

import cs3500.pyramidsolitaire.model.hw02.Card.Rank;
import cs3500.pyramidsolitaire.model.hw02.Card.Suit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class representing a basic model of Pyramid Solitaire.
 */
public class BasicPyramidSolitaire implements PyramidSolitaireModel<Card> {
  protected boolean gameStarted;
  protected final List<List<Card>> pyramid;
  protected final List<Card> stock;
  protected final List<Card> draw;

  /**
   * Initialises the game without starting it, setting each list field to an empty ArrayList.
   */
  public BasicPyramidSolitaire() {
    this.gameStarted = false;
    this.pyramid = new ArrayList<>();
    this.stock = new ArrayList<>();
    this.draw = new ArrayList<>();
  }

  /**
   * Creates and returns a deck of 52 cards in a set order.
   * Rank order: Ace -> King.
   * Suit order: Clubs -> Diamonds -> Hearts -> Spades.
   * @return returns the deck in order.
   */
  @Override
  public List<Card> getDeck() {
    List<Card> result = new ArrayList<>();
    List<Suit> suitList = Arrays.asList(Suit.CLUBS, Suit.DIAMONDS,
        Suit.HEARTS, Suit.SPADES);

    for (int suitNum = 0 ; suitNum < 4 ; suitNum++) {
      for (int rankNum = 1 ; rankNum < 14 ; rankNum++) {
        result.add(new Card(Rank.valueOf(rankNum), suitList.get(suitNum)));
      }
    }
    return result;
  }


  /**
   * Implementation of startGame.
   * @param deck      the deck to be dealt.
   * @param shuffle   if {@code false}, use the order as given by {@code deck},
   *                  otherwise use a randomly shuffled order.
   * @param numRows   number of rows in the pyramid.
   * @param numDraw   number of draw cards available at a time.
   * @throws IllegalArgumentException if the deck is null or invalid,
   *                  the number of pyramid rows is non-positive,
   *                  the number of draw cards available at a time is negative,
   *                  or a full pyramid and draw pile cannot be dealt with the number of
   *                  given cards in deck
   */
  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException {

    if (deck == null) {
      throw new IllegalArgumentException("Deck is null");
    } else if (!checkDeck(deck)) {
      throw new IllegalArgumentException("Deck is invalid");
    } else if (numRows < 1) {
      throw new IllegalArgumentException("Number of rows must be positive");
    } else if (numDraw < 0) {
      throw new IllegalArgumentException("Number of draw cards must not be negative");
    } else if (checkNotEnoughCards(deck, numRows, numDraw)) {
      throw new IllegalArgumentException("Not enough cards in the deck to deal a valid game");
    }

    ArrayList<Card> deckClone = new ArrayList<>();
    for (Card c : deck) {
      deckClone.add(c.clone());
    }

    if (shuffle) {
      Collections.shuffle(deckClone);
    }

    pyramid.addAll(buildPyramid(deckClone, numRows));

    //Taking the last arraylist in the list as stock and remove it from the pyramid.
    stock.addAll(pyramid.get(pyramid.size() - 1));
    pyramid.remove(pyramid.size() - 1);

    for (int i = 0 ; i < numDraw ; i++) {
      draw.add(stock.get(0));
      stock.remove(0);
    }


    gameStarted = true;

  }

  /**
   * Builds a pyramid of cards based on the number of rows and the deck given.
   * Remaining cards are added to the end of the 2D Array to be removed later.
   * @param deck given deck.
   * @param numRows number of rows in the pyramid.
   * @return returns a 2D Array of the pyramid.
   * @throws IllegalArgumentException if the deck is null or numRows is not positive.
   */
  protected ArrayList<ArrayList<Card>> buildPyramid(List<Card> deck, int numRows) throws
      IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("Deck is null");
    } else if (numRows < 1) {
      throw new IllegalArgumentException("Number of rows must be positive");
    }
    ArrayList<ArrayList<Card>> result = new ArrayList<>();

    for (int rowNum = 1 ; rowNum <= numRows ; rowNum++) {
      ArrayList<Card> row = new ArrayList<>();
      while (row.size() < rowNum && deck.size() >= 1) {
        row.add(deck.get(0));
        deck.remove(0);
      }
      result.add(row);
    }
    result.add((ArrayList<Card>)deck);
    return result;
  }

  /**
   * Checks the validity of the deck by checking for duplicate cards and deck size.
   * @param deck The deck to check.
   * @return True or false depending on if the deck is valid or not.
   * @throws IllegalArgumentException if deck is null.
   */
  protected boolean checkDeck(List<Card> deck) throws IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("Deck is null");
    }
    boolean result = true;
    for (int i = 0 ; i < deck.size() ; i++) {
      for (int j = i + 1 ; j < deck.size() ; j++) {
        if (deck.get(i).equals(deck.get(j))) {
          result = false;
          break;
        }
      }
    }
    if (deck.size() < 52) {
      result = false;
    }
    return result;
  }

  /**
   * Checks if a complete pyramid can be built and a full draw hand can be dealt with the
   * given deck.
   * @param deck Deck to check.
   * @param numRows number of rows in the pyramid.
   * @param numDraw number of cards in the draw hand.
   * @return True if there are not enough cards to deal a game.
   * @throws IllegalArgumentException if deck is null, numRows is not positive,
   *                  or numDraw is negative.
   */
  protected boolean checkNotEnoughCards(List<Card> deck, int numRows, int numDraw) throws
      IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("Deck is null");
    } else if (numRows < 1) {
      throw new IllegalArgumentException("Number of rows must be positive");
    } else if (numDraw < 0) {
      throw new IllegalArgumentException("Number of draw cards must not be negative");
    }
    int cardsNeeded = ((numRows * (numRows + 1)) / 2) + numDraw;
    return deck.size() < cardsNeeded;
  }


  /**
   * Checks if a certain card is eligible to be removed (ie. no cards covering it up).
   * @param row from top to bottom (indexed from 0).
   * @param card from left to right (indexed from 0).
   * @return Whether or not the card can be removed.
   * @throws IllegalArgumentException if target is out of bounds.
   */
  protected boolean canBeRemoved(int row, int card) throws IllegalArgumentException {
    boolean result = true;
    if (row < 0 || row >= pyramid.size()) {
      throw new IllegalArgumentException("Target is out of bounds.");
    }
    if (row < pyramid.size() - 1) {
      if (card < 0 || card >= pyramid.get(row).size()) {
        throw new IllegalArgumentException("Target is out of bounds.");
      }
      result = (pyramid.get(row + 1).get(card) == null
          && pyramid.get(row + 1).get(card + 1) == null);
    }
    if (getCardAt(row, card) == null) {
      result = false;
    }
    return result;
  }

  /**
   * Remove two exposed cards on the pyramid, using the two specified card positions.
   * @param row1    row of first card position, numbered from 0 from the top of the pyramid.
   * @param card1   card of first card position, numbered from 0 from left.
   * @param row2    row of second card position.
   * @param card2   card of second card position.
   * @throws IllegalArgumentException if the attempted remove is invalid.
   * @throws IllegalStateException if the game has not yet been started.
   */
  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    if (!canBeRemoved(row1, card1) || !canBeRemoved(row2, card2)) {
      throw new IllegalArgumentException("Cards cannot be removed yet.");
    }
    Card c1 = pyramid.get(row1).get(card1);
    Card c2 = pyramid.get(row2).get(card2);
    if (c1.getRank() + c2.getRank() != 13) {
      throw new IllegalArgumentException("Cards don't add up to 13.");
    } else if (c1.getRank() + c2.getRank() == 13) {
      pyramid.get(row1).set(card1, null);
      pyramid.get(row2).set(card2, null);
    }
  }

  /**
   * Remove a single card on the pyramid, using the specified card position.
   * @param row    row of the desired card position, numbered from 0 from the top of the pyramid
   * @param card   card of the desired card position, numbered from 0 from left
   * @throws IllegalArgumentException if the attempted remove is invalid
   * @throws IllegalStateException if the game has not yet been started
   */
  @Override
  public void remove(int row, int card) throws IllegalArgumentException, IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    if (!canBeRemoved(row, card)) {
      throw new IllegalArgumentException("Card cannot be removed yet.");
    }
    Card c = pyramid.get(row).get(card);
    if (c.getRank() != 13) {
      throw new IllegalArgumentException("Card is not a King.");
    } else if (c.getRank() == 13) {
      pyramid.get(row).set(card, null);
    }
  }

  /**
   * Remove two cards, one from the draw pile and one from the pyramid.
   * @param drawIndex the card from the draw pile, numbered from 0 from left
   * @param row    row of the desired card position, numbered from 0 from the top of the pyramid
   * @param card   card of the desired card position, numbered from 0 from left
   * @throws IllegalArgumentException if the attempted remove is invalid
   * @throws IllegalStateException if the game has not yet been started
   */
  @Override
  public void removeUsingDraw(int drawIndex, int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (!canBeRemoved(row, card)) {
      throw new IllegalArgumentException("Cards cannot be removed yet.");
    }
    if (drawIndex < 0 || drawIndex >= getDrawCards().size()) {
      throw new IllegalArgumentException("drawIndex is out of bounds.");
    }
    Card c = pyramid.get(row).get(card);
    Card d = draw.get(drawIndex);
    if (c.getRank() + d.getRank() != 13) {
      throw new IllegalArgumentException("Cards don't add up to 13.");
    } else if (c.getRank() + d.getRank() == 13) {
      pyramid.get(row).set(card, null);
      if (stock.size() > 0) {
        draw.set(drawIndex, stock.get(0));
        stock.remove(0);
      } else {
        draw.remove(drawIndex);
      }
    }
  }

  /**
   * Discards an individual card from the draw pile.
   * @param drawIndex the card from the draw pile to be discarded.
   * @throws IllegalArgumentException if the index is invalid or no card is present there.
   * @throws IllegalStateException if the game has not yet been started.
   */
  @Override
  public void discardDraw(int drawIndex) throws IllegalArgumentException, IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    if (drawIndex > draw.size() - 1 || drawIndex < 0) {
      throw new IllegalArgumentException("Invalid Draw Index.");
    }
    if (stock.size() > 0) {
      draw.set(drawIndex, stock.get(0));
      stock.remove(0);
    } else {
      draw.remove(drawIndex);
    }
  }

  /**
   * Returns the number of rows originally in the pyramid, or -1 if the game hasn't been started.
   * @return the height of the pyramid, or -1.
   */
  @Override
  public int getNumRows() {
    if (!gameStarted) {
      return -1;
    }
    return pyramid.size();
  }

  /**
   * Returns the maximum number of visible cards in the draw pile,
   * or -1 if the game hasn't been started.
   *
   * @return the number of visible cards in the draw pile, or -1
   */
  @Override
  public int getNumDraw() {
    if (!gameStarted) {
      return - 1;
    } else {
      return draw.size();
    }
  }

  /**
   * Returns the width of the requested row, measured from
   * the leftmost card to the rightmost card (inclusive) as the game is initially dealt.
   * @param row the desired row (0-indexed)
   * @return the number of spaces needed to deal out that row
   * @throws IllegalArgumentException if the row is invalid
   * @throws IllegalStateException if the game has not yet been started
   */
  @Override
  public int getRowWidth(int row) throws IllegalArgumentException, IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (row > pyramid.size() - 1 || row < 0) {
      throw new IllegalArgumentException("Invalid Row Index");
    }
    return pyramid.get(row).size();
  }

  /**
   * Signal if the game is over or not. A game is said to be over if there are no possible removes
   * or discards.
   *
   * @return true if game is over, false otherwise
   * @throws IllegalStateException if the game hasn't been started yet
   */
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

  /**
   * Return the current score, which is the sum of the values of the cards
   * remaining in the pyramid.
   * @return the score.
   * @throws IllegalStateException if the game hasn't been started yet.
   */
  @Override
  public int getScore() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    return getScoreHelp();
  }

  /**
   * Helper for getScore() since this function is needed in isGameOver() too.
   * @return the current score.
   */
  protected int getScoreHelp() {
    int result = 0;
    for (List<Card> cards : pyramid) {
      for (Card c : cards) {
        if (c != null) {
          result += c.getRank();
        }
      }
    }
    return result;
  }

  /**
   * Returns the card at the specified coordinates.
   * @param row   row of the desired card (0-indexed from the top).
   * @param card  column of the desired card (0-indexed from the left).
   * @return the card at the given position, or <code>null</code> if no card is there.
   * @throws IllegalArgumentException if the coordinates are invalid.
   * @throws IllegalStateException if the game hasn't been started yet.
   */
  @Override
  public Card getCardAt(int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (row > pyramid.size() - 1 || row < 0) {
      throw new IllegalArgumentException("Invalid row");
    }
    if (card > pyramid.get(row).size() - 1 || card < 0) {
      throw new IllegalArgumentException("Invalid card");
    }

    if (pyramid.get(row).get(card) == null) {
      return null;
    } else {
      return pyramid.get(row).get(card);
    }
  }

  /**
   * Returns the currently available draw cards.
   * There should be at most {@link PyramidSolitaireModel#getNumDraw} cards (the number
   * specified when the game started) -- there may be fewer, if cards have been removed.
   * @return the ordered list of available draw cards.
   * @throws IllegalStateException if the game hasn't been started yet.
   */
  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    return draw;
  }
}
