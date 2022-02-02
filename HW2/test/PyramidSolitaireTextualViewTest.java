import static org.junit.Assert.assertEquals;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Card.Rank;
import cs3500.pyramidsolitaire.model.hw02.Card.Suit;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

/**
 * Tests for PyramidSolitaireTextualView.
 */
public class PyramidSolitaireTextualViewTest {

  BasicPyramidSolitaire bps1;
  Card c1;
  Card c2;
  Card c3;
  Card c4;
  Card c5;
  Card c6;
  Card c7;
  Card c8;
  Card c9;
  Card c10;
  Card c11;
  List<Card> customDeck;
  PyramidSolitaireTextualView bpsView;

  protected void initData() {
    this.bps1 = new BasicPyramidSolitaire();
    c1 = new Card(Rank.valueOf(13), Suit.DIAMONDS);
    c2 = new Card(Rank.valueOf(10), Suit.CLUBS);
    c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    c4 = new Card(Rank.valueOf(10), Suit.HEARTS);
    c5 = new Card(Rank.valueOf(3), Suit.HEARTS);
    c6 = new Card(Rank.valueOf(13), Suit.SPADES);
    c7 = new Card(Rank.valueOf(1), Suit.SPADES);
    c8 = new Card(Rank.valueOf(2), Suit.SPADES);
    c9 = new Card(Rank.valueOf(3), Suit.SPADES);
    c10 = new Card(Rank.valueOf(4), Suit.SPADES);
    c11 = new Card(Rank.valueOf(5), Suit.SPADES);
    customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5, c6,
        c7, c8, c9, c10, c11));
  }

  @Test
  public void testToStringStart() {
    initData();
    bps1.startGameAllowInvalid(customDeck, false, 3, 2);
    bpsView = new PyramidSolitaireTextualView(bps1);
    assertEquals(bpsView.toString(), "    K♦\n  10♣ 3♣\n10♥ 3♥  K♠\nDraw: A♠, 2♠");
  }

  @Test
  public void testToStringMove1() {
    initData();
    bps1.startGameAllowInvalid(customDeck, false, 3, 2);
    bpsView = new PyramidSolitaireTextualView(bps1);
    bps1.discardDraw(0);
    assertEquals(bpsView.toString(), "    K♦\n  10♣ 3♣\n10♥ 3♥  K♠\nDraw: 3♠, 2♠");
  }

  @Test
  public void testToStringMove2() {
    initData();
    bps1.startGameAllowInvalid(customDeck, false, 3, 2);
    bpsView = new PyramidSolitaireTextualView(bps1);
    bps1.discardDraw(0);
    bps1.remove(2, 0, 2, 1);
    assertEquals(bpsView.toString(), "    K♦\n  10♣ 3♣\n.   .   K♠\nDraw: 3♠, 2♠");
  }

  @Test
  public void testToStringMove3() {
    initData();
    bps1.startGameAllowInvalid(customDeck, false, 3, 2);
    bpsView = new PyramidSolitaireTextualView(bps1);
    bps1.discardDraw(0);
    bps1.remove(2, 0, 2, 1);
    bps1.removeUsingDraw(0, 1, 0);
    assertEquals(bpsView.toString(), "    K♦\n  .   3♣\n.   .   K♠\nDraw: 4♠, 2♠");
  }

  @Test
  public void testToStringMove4() {
    initData();
    bps1.startGameAllowInvalid(customDeck, false, 3, 2);
    bpsView = new PyramidSolitaireTextualView(bps1);
    bps1.discardDraw(0);
    bps1.remove(2, 0, 2, 1);
    bps1.removeUsingDraw(0, 1, 0);
    bps1.remove(2, 2);
    assertEquals(bpsView.toString(), "    K♦\n  .   3♣\n\nDraw: 4♠, 2♠");
  }

  @Test
  public void testToStringMove5() {
    initData();
    bps1.startGameAllowInvalid(customDeck, false, 3, 2);
    bpsView = new PyramidSolitaireTextualView(bps1);
    bps1.discardDraw(0);
    bps1.remove(2, 0, 2, 1);
    bps1.removeUsingDraw(0, 1, 0);
    bps1.remove(2, 2);
    assertEquals(bpsView.toString(), "    K♦\n  .   3♣\n\nDraw: 4♠, 2♠");
  }

  @Test
  public void testToStringGameOver() {
    initData();
    bps1.startGameAllowInvalid(customDeck, false, 3, 2);
    bpsView = new PyramidSolitaireTextualView(bps1);
    bps1.discardDraw(0);
    bps1.remove(2, 0, 2, 1);
    bps1.removeUsingDraw(0, 1, 0);
    bps1.remove(2, 2);
    bps1.discardDraw(1);
    assertEquals(bpsView.toString(), "Game over. Score: 16");
  }

  @Test
  public void testToStringGameNotStarted() {
    initData();
    bps1.buildPyramid(customDeck, 3);
    bpsView = new PyramidSolitaireTextualView(bps1);
    assertEquals(bpsView.toString(), "");
  }

  @Test
  public void testToStringWin() {
    initData();
    Card lastCard = new Card(Rank.valueOf(13), Suit.HEARTS);
    ArrayList<Card> customDeck2 = new ArrayList<>(Collections.singletonList(lastCard));
    bps1.startGameAllowInvalid(customDeck2, false,1,0);
    PyramidSolitaireTextualView bpsView2 = new PyramidSolitaireTextualView(bps1);
    bps1.remove(0,0);
    assertEquals(bpsView2.toString(), "You win!");
  }

}
