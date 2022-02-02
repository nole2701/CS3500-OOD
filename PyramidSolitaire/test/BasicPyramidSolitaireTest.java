import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Card.Rank;
import cs3500.pyramidsolitaire.model.hw02.Card.Suit;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * Tests for BasicPyramidSolitaire.
 */
public class BasicPyramidSolitaireTest {

  private List<Card> modifyDeck(List<Card> customDeck) {
    BasicPyramidSolitaire model = new BasicPyramidSolitaire();
    List<Card> fullDeck = model.getDeck();
    for (Card c: customDeck) {
      for (int d = 0 ; d <= fullDeck.size() - 1 ; d++) {
        if (c.equals(fullDeck.get(d))) {
          fullDeck.remove(d);
        }
      }
    }
    customDeck.addAll(fullDeck);
    return customDeck;
  }

  PyramidSolitaireModel<Card> bps1;
  Card card1C;
  Card cardKH;

  protected void initData() {
    this.bps1 = PyramidSolitaireCreator.create(GameType.BASIC);
    this.card1C = new Card(Rank.valueOf(1), Suit.CLUBS);
    this.cardKH = new Card(Rank.valueOf(13), Suit.HEARTS);
  }


  /**
   * Testing getDeck() by calling a particular card from the whole deck.
   */
  @Test
  public void testGetDeck1Club() {
    initData();
    assertEquals(card1C, bps1.getDeck().get(0));
  }

  @Test
  public void testGetDeckKingHeart() {
    initData();
    assertEquals(cardKH, bps1.getDeck().get(38));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetDeckOutOfBounds1() {
    initData();
    Card badCard = bps1.getDeck().get(52);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetDeckOutOfBounds2() {
    initData();
    Card badCard = bps1.getDeck().get(-1);
  }

  /**
   * Testing getting the values of the cards from the deck.
   */
  @Test
  public void testGetRank1() {
    initData();
    Card card = bps1.getDeck().get(38);
    assertEquals(13, card.getRank());
  }

  @Test
  public void testGetSuit1() {
    initData();
    Card card = bps1.getDeck().get(38);
    assertEquals("♥", card.getSuit());
  }



  /**
   * Testing startGame().
   */
  @Test
  public void testStartGameSuccess() {
    initData();
    assertEquals(bps1.getNumRows(), -1);
    bps1.startGame(bps1.getDeck(), false, 4, 2);
    assertEquals(bps1.getNumRows(), 4);
    assertEquals(bps1.getCardAt(0,0), card1C);
    assertEquals(bps1.getCardAt(1,0), new Card(Rank.valueOf(2), Suit.CLUBS));
    assertEquals(bps1.getCardAt(1, 1), new Card(Rank.valueOf(3), Suit.CLUBS));
    assertEquals(bps1.getCardAt(2,0), new Card(Rank.valueOf(4), Suit.CLUBS));
    assertEquals(bps1.getCardAt(2,1), new Card(Rank.valueOf(5), Suit.CLUBS));
    assertEquals(bps1.getCardAt(2,2), new Card(Rank.valueOf(6), Suit.CLUBS));
    assertEquals(bps1.getCardAt(3,0), new Card(Rank.valueOf(7), Suit.CLUBS));
    assertEquals(bps1.getCardAt(3,1), new Card(Rank.valueOf(8), Suit.CLUBS));
    assertEquals(bps1.getCardAt(3,3), new Card(Rank.valueOf(10), Suit.CLUBS));

    boolean caughtExcessRow = false;
    boolean caughtExcessCard = false;
    try {
      bps1.getCardAt(4,0);
    } catch (IllegalArgumentException e) {
      caughtExcessRow = true;
    }
    try {
      bps1.getCardAt(3,4);
    } catch (IllegalArgumentException e) {
      caughtExcessCard = true;
    }
    assertTrue(caughtExcessRow);
    assertTrue(caughtExcessCard);

    assertEquals(bps1.getDrawCards().get(0), new Card(Rank.valueOf(11), Suit.CLUBS));
    assertEquals(bps1.getDrawCards().get(1), new Card(Rank.valueOf(12), Suit.CLUBS));

    boolean caughtExcessDraw = false;
    try {
      bps1.getDrawCards().get(2);
    } catch (IndexOutOfBoundsException e) {
      caughtExcessDraw = true;
    }
    assertTrue(caughtExcessDraw);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNullDeck() {
    initData();
    bps1.startGame(null, false, 4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeck() {
    initData();
    List<Card> badDeck = bps1.getDeck();
    badDeck.add(card1C);
    badDeck.remove(cardKH);
    bps1.startGame(badDeck, false, 4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGame0numRows() {
    initData();
    bps1.startGame(bps1.getDeck(), false, 0, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNegativeNumDraw() {
    initData();
    bps1.startGame(bps1.getDeck(), false, 4, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNotEnoughCards() {
    initData();
    bps1.startGame(bps1.getDeck(), false, 20, 2);
  }

  /**
   * Testing remove() for removing 2 cards.
   */
  @Test
  public void testRemove2Success() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(10), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));
    modifyDeck(customDeck);
    bps1.startGame(customDeck, false, 2, 1);
    assertEquals(bps1.getCardAt(1, 0), c2);
    assertEquals(bps1.getCardAt(1, 1), c3);
    bps1.remove(1, 0, 1,1);
    assertNull(bps1.getCardAt(1, 0));
    assertNull(bps1.getCardAt(1, 1));
  }

  @Test(expected = IllegalStateException.class)
  public void testRemove2GameNotStarted() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(10), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));
    bps1.remove(1,0, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemove2CardIsCovered() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(10), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));
    bps1.startGame(customDeck, false, 2, 1);
    bps1.remove(0,0, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemove2NotSum13() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(10), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(5), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));
    bps1.startGame(customDeck, false, 2, 1);
    bps1.remove(1,0, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemove2OutOfBounds() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(10), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(5), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));
    bps1.startGame(customDeck, false, 2, 1);
    bps1.remove(5,0, 1, 1);
  }

  /**
   * Testing remove() for removing a single card (King).
   */
  @Test
  public void testRemove1Success() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(10), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));
    modifyDeck(customDeck);
    bps1.startGame(customDeck, false, 2, 1);
    assertEquals(bps1.getCardAt(1, 0), c2);
    assertEquals(bps1.getCardAt(1, 1), c3);
    bps1.remove(1, 0, 1,1);
    assertNull(bps1.getCardAt(1, 0));
    assertNull(bps1.getCardAt(1, 1));
    bps1.remove(0, 0);
    assertNull(bps1.getCardAt(0, 0));
  }

  @Test(expected = IllegalStateException.class)
  public void testRemove1GameNotStarted() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(10), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));
    bps1.remove(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemove1CardIsCovered() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(10), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));
    bps1.startGame(customDeck, false, 2, 1);
    bps1.remove(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemove1Not13() {
    initData();
    Card c1 = new Card(Rank.valueOf(11), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(10), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(5), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));
    bps1.startGame(customDeck, false, 1, 1);
    bps1.remove(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemove1OutOfBounds() {
    initData();
    Card c1 = new Card(Rank.valueOf(11), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(10), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(5), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));
    bps1.startGame(customDeck, false, 1, 1);
    bps1.remove(5, 0);
  }

  /**
   * Testing removeUsingDraw().
   */
  @Test
  public void testRemoveDrawSuccess() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(10), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    Card c6 = new Card(Rank.valueOf(7), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5, c6));
    modifyDeck(customDeck);
    bps1.startGame(customDeck, false, 2, 1);
    assertEquals(bps1.getCardAt(1, 0), c2);
    assertEquals(bps1.getDrawCards().get(0), c4);
    bps1.removeUsingDraw(0, 1, 0);
    assertNull(bps1.getCardAt(1, 0));
    assertEquals(bps1.getDrawCards().get(0), c5);
  }

  @Test(expected = IllegalStateException.class)
  public void testRemoveUsingDrawGameNotStarted() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(10), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    Card c6 = new Card(Rank.valueOf(7), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5, c6));
    bps1.removeUsingDraw(0, 1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingDrawCardIsCovered() {
    initData();
    Card c1 = new Card(Rank.valueOf(10), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    Card c6 = new Card(Rank.valueOf(7), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5, c6));
    bps1.startGame(customDeck, false, 2, 1);
    bps1.removeUsingDraw(0, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingDrawSumNot13() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(11), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    Card c6 = new Card(Rank.valueOf(7), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5, c6));
    bps1.startGame(customDeck, false, 2, 1);
    bps1.removeUsingDraw(0, 1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingDrawOutOfBoundsDraw() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(11), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    Card c6 = new Card(Rank.valueOf(7), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5, c6));
    bps1.startGame(customDeck, false, 2, 1);
    bps1.removeUsingDraw(5, 1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingDrawOutOfBoundsPyramid() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(11), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    Card c6 = new Card(Rank.valueOf(7), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5, c6));
    bps1.startGame(customDeck, false, 2, 1);
    bps1.removeUsingDraw(0, 5, 0);
  }

  /**
   * Testing discardDraw().
   */
  @Test
  public void testDiscardDrawSuccessStockLeft() {
    initData();
    bps1.startGame(bps1.getDeck(), false, 2, 2);
    assertEquals(bps1.getDrawCards().get(0), new Card(Rank.valueOf(4), Suit.CLUBS));
    assertEquals(bps1.getDrawCards().get(1), new Card(Rank.valueOf(5), Suit.CLUBS));
    bps1.discardDraw(1);
    assertEquals(bps1.getDrawCards().get(0), new Card(Rank.valueOf(4), Suit.CLUBS));
    assertEquals(bps1.getDrawCards().get(1), new Card(Rank.valueOf(6), Suit.CLUBS));
  }

  @Test(expected = IllegalStateException.class)
  public void testDiscardDrawGameNotStarted() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(11), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(3), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
    bps1.discardDraw(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDiscardDrawInvalidIndex1() {
    initData();
    bps1.startGame(bps1.getDeck(), false, 2, 2);
    bps1.discardDraw(2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDiscardDrawInvalidIndex2() {
    initData();
    bps1.startGame(bps1.getDeck(), false, 2, 2);
    bps1.discardDraw(-1);
  }

  /**
   * Testing getNumRows().
   */
  @Test
  public void testGetNumRowsSuccess() {
    initData();
    bps1.startGame(bps1.getDeck(), false, 3, 2);
    assertEquals(bps1.getNumRows(), 3);
  }

  @Test
  public void testGetNumRowsGameNotStarted() {
    initData();
    assertEquals(bps1.getNumRows(), -1);
  }

  /**
   * testing getNumDraw().
   */
  @Test
  public void testGetNumDrawSuccess() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(11), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(3), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
    modifyDeck(customDeck);
    bps1.startGame(customDeck, false, 2, 49);
    assertEquals(bps1.getDrawCards().get(0), c4);
    assertEquals(bps1.getNumDraw(), 49);
    bps1.discardDraw(0);
    assertEquals(bps1.getNumDraw(), 48);
  }

  @Test
  public void testGetNumDrawGameNotStarted() {
    initData();
    assertEquals(bps1.getNumDraw(), -1);
  }

  /**
   * Testing getRowWidth().
   */
  @Test
  public void testGetRowWidthSuccess() {
    initData();
    bps1.startGame(bps1.getDeck(), false, 4, 2);
    assertEquals(bps1.getRowWidth(0), 1);
    assertEquals(bps1.getRowWidth(1), 2);
    assertEquals(bps1.getRowWidth(2), 3);
    assertEquals(bps1.getRowWidth(3), 4);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetRowWidthGameNotStarted() {
    initData();
    bps1.getRowWidth(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetRowWidthInvalidRow1() {
    initData();
    bps1.startGame(bps1.getDeck(), false, 4, 2);
    bps1.getRowWidth(5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetRowWidthInvalidRow2() {
    initData();
    bps1.startGame(bps1.getDeck(), false, 4, 2);
    bps1.getRowWidth(-1);
  }

  /**
   * Testing isGameOver().
   */
  @Test
  public void testIsGameOverComboInDeck() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(10), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    Card c6 = new Card(Rank.valueOf(7), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5, c6));
    modifyDeck(customDeck);
    bps1.startGame(customDeck, false, 2, 1);
    assertFalse(bps1.isGameOver());
  }

  @Test
  public void testIsGameOverKingAvailable() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(10), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    Card c6 = new Card(Rank.valueOf(7), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5, c6));
    modifyDeck(customDeck);
    bps1.startGame(customDeck, false, 2, 1);
    bps1.remove(1,0,1,1);
    assertFalse(bps1.isGameOver());
  }

  @Test
  public void testIsGameOverComboWithDraw() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(10), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    Card c6 = new Card(Rank.valueOf(7), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5, c6));
    modifyDeck(customDeck);
    bps1.startGame(customDeck, false, 2, 1);
    assertFalse(bps1.isGameOver());
  }

  @Test
  public void testIsGameOverDiscardAvailable() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(10), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(5), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    Card c6 = new Card(Rank.valueOf(7), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5, c6));
    modifyDeck(customDeck);
    bps1.startGame(customDeck, false, 2, 1);
    assertFalse(bps1.isGameOver());
  }

  @Test
  public void testIsGameOverNoMoreMoves() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(10), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(5), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
    modifyDeck(customDeck);
    bps1.startGame(customDeck, false, 2, 0);
    assertTrue(bps1.isGameOver());
  }

  /**
   * Testing getScore().
   */
  @Test
  public void testGetScoreSuccess() {
    initData();
    bps1.startGame(bps1.getDeck(), false, 3, 2);
    int score = 1 + 2 + 3 + 4 + 5 + 6;
    assertEquals(bps1.getScore(), score);
  }

  @Test
  public void testGetScoreWithNull() {
    initData();
    Card c1 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(10), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(6), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));
    modifyDeck(customDeck);
    bps1.startGame(customDeck, false, 2, 1);
    assertEquals(bps1.getScore(), 26);
    bps1.remove(1, 0, 1,1);
    assertEquals(bps1.getScore(), 13);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetScoreGameNotStarted() {
    initData();
    bps1.getScore();
  }

  /**
   * Testing getCardAt().
   */
  @Test
  public void testGetCardAtSuccess() {
    initData();
    bps1.startGame(bps1.getDeck(), false, 4, 2);
    assertEquals(bps1.getCardAt(0, 0), card1C);
    assertEquals(bps1.getCardAt(1, 0), new Card(Rank.valueOf(2), Suit.CLUBS));
    assertEquals(bps1.getCardAt(3, 1), new Card(Rank.valueOf(8), Suit.CLUBS));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtInvalidRow() {
    initData();
    bps1.startGame(bps1.getDeck(), false, 4, 2);
    bps1.getCardAt(10, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtInvalidCard() {
    initData();
    bps1.startGame(bps1.getDeck(), false, 4, 2);
    bps1.getCardAt(0, 10);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCardAtGameNotStarted() {
    initData();
    bps1.getCardAt(0, 0);
  }


  /**
   * Testing getDrawCards().
   */
  @Test
  public void testGetDrawCardsGameStarted() {
    initData();
    bps1.startGame(bps1.getDeck(), false, 1, 2);
    List<Card> drawHand = bps1.getDrawCards();
    assertEquals(drawHand.get(0), new Card(Rank.valueOf(2), Suit.CLUBS));
    assertEquals(drawHand.get(1), new Card(Rank.valueOf(3), Suit.CLUBS));
    boolean caughtExcessDraw = false;
    try {
      bps1.getDrawCards().get(2);
    } catch (IndexOutOfBoundsException e) {
      caughtExcessDraw = true;
    }
    assertTrue(caughtExcessDraw);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetDrawCardsGameNotStarted() {
    initData();
    List<Card> drawHand = bps1.getDrawCards();
  }



}
