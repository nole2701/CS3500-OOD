import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Card.Rank;
import cs3500.pyramidsolitaire.model.hw02.Card.Suit;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * Testing to see if MultiPyramidSolitaire follows the basic rules and not the relaxed rules.
 * Multi commands are inherited from Basic so if they work successfully, then error handling is
 * identical to Basic (which has already been tested).
 */
public class MultiPyramidSolitaireTest {

  private List<Card> modifyDeck(List<Card> customDeck) {
    BasicPyramidSolitaire model = new MultiPyramidSolitaire();
    List<Card> fullDeck = model.getDeck();
    for (Card c: customDeck) {
      for (int d = 0 ; d <= fullDeck.size() - 1 ; d++) {
        if (c.equals(fullDeck.get(d))) {
          fullDeck.remove(d);
          break;
        }
      }
    }
    customDeck.addAll(fullDeck);
    return customDeck;
  }

  PyramidSolitaireModel<Card> mps1;
  Card card1C;
  Card cardKH;

  protected void initData() {
    this.mps1 = PyramidSolitaireCreator.create(GameType.MULTIPYRAMID);
    this.card1C = new Card(Rank.valueOf(1), Suit.CLUBS);
    this.cardKH = new Card(Rank.valueOf(13), Suit.HEARTS);
  }

  @Test
  public void testMultiGameOver() {
    initData();
    mps1.startGame(mps1.getDeck(), false, 2, 0);
    assertFalse(mps1.isGameOver());
    mps1.remove(1, 3, 1, 2);
    assertTrue(mps1.isGameOver());
  }

  @Test
  public void testMultiRemove2() {
    initData();
    mps1.startGame(mps1.getDeck(), false, 2, 0);
    assertNotNull((mps1.getCardAt(1, 3)));
    assertNotNull((mps1.getCardAt(1, 2)));
    mps1.remove(1, 3, 1, 2);
    assertNull(mps1.getCardAt(1, 3));
    assertNull(mps1.getCardAt(1, 2));
  }

  @Test
  public void testMultiRemove1() {
    initData();
    mps1.startGame(mps1.getDeck(), false, 5, 0);
    assertNotNull((mps1.getCardAt(4, 1)));
    mps1.remove(4, 1);
    assertNull((mps1.getCardAt(4, 1)));
  }

  @Test
  public void testMultiRemoveUsingDraw() {
    initData();
    mps1.startGame(mps1.getDeck(), false, 3, 5);
    assertNotNull((mps1.getCardAt(2, 4)));
    mps1.removeUsingDraw(1,2, 4);
    assertNull((mps1.getCardAt(2, 4)));
  }

  @Test
  public void testMultiDiscardDraw() {
    initData();
    mps1.startGame(mps1.getDeck(), false, 3, 5);
    Card c1 = mps1.getDrawCards().get(0);
    mps1.discardDraw(0);
    assertNotEquals(mps1.getDrawCards().get(0), c1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiDoesNotFollowRelaxed() {
    initData();
    Card c1 = new Card(Rank.valueOf(1), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(2), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(5), Suit.CLUBS);
    Card c6 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c7 = new Card(Rank.valueOf(10), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5,  c6, c7));
    modifyDeck(customDeck);
    mps1.startGame(customDeck, false, 2, 5);
    mps1.remove(1, 2);
    assertNull(mps1.getCardAt(1, 2));
    mps1.remove(1, 3, 0, 2);
  }

}
