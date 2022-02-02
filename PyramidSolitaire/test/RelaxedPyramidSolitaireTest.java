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
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * Tests for RelaxedPyramidSolitaire. Tests the new rules to see if they work, and tests basic
 * rules to see if the basic version wont follow relaxed rules.
 */
public class RelaxedPyramidSolitaireTest {

  private List<Card> modifyDeck(List<Card> customDeck) {
    BasicPyramidSolitaire model = new RelaxedPyramidSolitaire();
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

  PyramidSolitaireModel<Card> rps1;
  BasicPyramidSolitaire bps1;
  Card card1C;
  Card cardKH;

  protected void initData() {
    this.rps1 = PyramidSolitaireCreator.create(GameType.RELAXED);
    this.bps1 = new BasicPyramidSolitaire();
    this.card1C = new Card(Rank.valueOf(1), Suit.CLUBS);
    this.cardKH = new Card(Rank.valueOf(13), Suit.HEARTS);
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
    rps1.startGame(customDeck, false, 2, 1);
    assertEquals(rps1.getCardAt(1, 0), c2);
    assertEquals(rps1.getCardAt(1, 1), c3);
    rps1.remove(1, 0, 1,1);
    assertNull(rps1.getCardAt(1, 0));
    assertNull(rps1.getCardAt(1, 1));
  }

  @Test
  public void testRemove2RelaxedSuccess() {
    initData();
    Card c1 = new Card(Rank.valueOf(1), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(12), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(10), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
    modifyDeck(customDeck);
    rps1.startGame(customDeck, false, 2, 1);
    assertEquals(rps1.getCardAt(1, 0), c2);
    assertEquals(rps1.getCardAt(1, 1), c3);
    rps1.removeUsingDraw(0, 1, 1);
    assertNull(rps1.getCardAt(1, 1));
    rps1.remove(1, 0, 0, 0);
    assertNull(rps1.getCardAt(1, 0));
    assertNull(rps1.getCardAt(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemove2RelaxedCovered() {
    initData();
    Card c1 = new Card(Rank.valueOf(1), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(12), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(10), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
    modifyDeck(customDeck);
    rps1.startGame(customDeck, false, 2, 1);
    rps1.remove(1, 0, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemove2RelaxedNot13() {
    initData();
    Card c1 = new Card(Rank.valueOf(1), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(11), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(10), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
    modifyDeck(customDeck);
    rps1.startGame(customDeck, false, 2, 1);
    assertEquals(rps1.getCardAt(1, 0), c2);
    assertEquals(rps1.getCardAt(1, 1), c3);
    rps1.removeUsingDraw(0, 1, 1);
    assertNull(rps1.getCardAt(1, 1));
    rps1.remove(1, 0, 0, 0);
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
    rps1.remove(1,0, 1, 1);
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
    rps1.startGame(customDeck, false, 2, 1);
    rps1.remove(0,0, 1, 1);
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
    rps1.startGame(customDeck, false, 2, 1);
    rps1.remove(1,0, 1, 1);
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
    rps1.startGame(customDeck, false, 2, 1);
    rps1.remove(5,0, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemove2RelaxedInBasic() {
    initData();
    Card c1 = new Card(Rank.valueOf(1), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(12), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(10), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
    modifyDeck(customDeck);
    bps1.startGame(customDeck, false, 2, 1);
    assertEquals(bps1.getCardAt(1, 0), c2);
    assertEquals(bps1.getCardAt(1, 1), c3);
    bps1.removeUsingDraw(0, 1, 1);
    assertNull(bps1.getCardAt(1, 1));
    bps1.remove(1, 0, 0, 0);
  }

  @Test
  public void testRelaxedGameOver() {
    initData();
    Card c1 = new Card(Rank.valueOf(1), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(12), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(10), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
    modifyDeck(customDeck);
    rps1.startGame(customDeck, false, 2, 0);
    bps1.startGame(customDeck, false, 2, 0);
    rps1.remove(1, 1);
    bps1.remove(1, 1);
    assertTrue(bps1.isGameOver());
    assertFalse(rps1.isGameOver());
    rps1.remove(1, 0, 0, 0);
    assertTrue(rps1.isGameOver());
  }

}
