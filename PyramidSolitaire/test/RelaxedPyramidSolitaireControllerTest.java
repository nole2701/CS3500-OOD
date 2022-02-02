import static org.junit.Assert.assertEquals;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Card.Rank;
import cs3500.pyramidsolitaire.model.hw02.Card.Suit;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * Class for testing Relaxed rules through a controller. Confirms if the relaxed rules work in
 * addition to basic rules. Confirms that multi-pyramid and basic versions
 * can't use relaxed removes.
 */
public class RelaxedPyramidSolitaireControllerTest {

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

  private List<Card> modifyDeckMulti(List<Card> customDeck) {
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

  PyramidSolitaireTextualController controller;
  StringReader in;
  StringBuilder out;
  RelaxedPyramidSolitaire rps1;
  Card card1C;
  Card cardKH;

  protected void initData() {
    this.rps1 = new RelaxedPyramidSolitaire();
    this.out = new StringBuilder();
    this.controller = new PyramidSolitaireTextualController(in, out);
    this.card1C = new Card(Rank.valueOf(1), Suit.CLUBS);
    this.cardKH = new Card(Rank.valueOf(13), Suit.HEARTS);
  }

  @Test
  public void testRemove2IllegalNot13() {
    this.in = new StringReader("rm2 2 1 2 2");
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 2, 1);
    assertEquals("  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n"
        + "Invalid move. Play again. Cards don't add up to 13.\n", out.toString());
  }

  @Test
  public void testRemove2IllegalCovered() {
    this.in = new StringReader("rm2 1 1 2 2");
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 2, 1);
    assertEquals("  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n"
        + "Invalid move. Play again. Cards cannot be removed yet.\n", out.toString());
  }

  @Test
  public void testRemove2IllegalOutOfBounds() {
    this.in = new StringReader("rm2 5 1 2 2");
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 2, 1);
    assertEquals("  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n"
        + "Invalid move. Play again. Target is out of bounds.\n", out.toString());
  }

  @Test
  public void testRemove2Success() {
    this.in = new StringReader("rm2 6 3 6 6");
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 6, 1);
    assertEquals("          A♣\n"
        + "        2♣  3♣\n"
        + "      4♣  5♣  6♣\n"
        + "    7♣  8♣  9♣  10♣\n"
        + "  J♣  Q♣  K♣  A♦  2♦\n"
        + "3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "Draw: 9♦\n"
        + "Score: 127\n"
        + "          A♣\n"
        + "        2♣  3♣\n"
        + "      4♣  5♣  6♣\n"
        + "    7♣  8♣  9♣  10♣\n"
        + "  J♣  Q♣  K♣  A♦  2♦\n"
        + "3♦  4♦  .   6♦  7♦  .\n"
        + "Draw: 9♦\n"
        + "Score: 114\n", out.toString());
  }

  @Test
  public void testRemove2RelaxedSuccess() {
    this.in = new StringReader("rmwd 1 2 2 rm2 2 1 1 1");
    Card c1 = new Card(Rank.valueOf(1), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(12), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(10), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
    modifyDeck(customDeck);
    initData();
    controller.playGame(rps1, customDeck, false, 2, 1);
    assertEquals("  A♣\n"
        + "Q♣  3♣\n"
        + "Draw:  10♣\n"
        + "Score: 16\n"
        + "  A♣\n"
        + "Q♣  .\n"
        + "Draw: 2♣\n"
        + "Score: 13\n"
        + "You win!", out.toString());
  }

  @Test
  public void testRemove2RelaxedFailsInBasic() {
    this.in = new StringReader("rmwd 1 2 2 rm2 2 1 1 1");
    Card c1 = new Card(Rank.valueOf(1), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(12), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(10), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
    modifyDeck(customDeck);
    initData();
    controller.playGame(new BasicPyramidSolitaire(), customDeck,
        false, 2, 1);
    assertEquals("  A♣\n"
        + "Q♣  3♣\n"
        + "Draw:  10♣\n"
        + "Score: 16\n"
        + "  A♣\n"
        + "Q♣  .\n"
        + "Draw: 2♣\n"
        + "Score: 13\n"
        + "Invalid move. Play again. Cards cannot be removed yet.\n", out.toString());
  }

  @Test
  public void testRemove2RelaxedFailsInMulti() {
    this.in = new StringReader("rm1 2 3 rm2 2 4 1 3");
    initData();
    Card c1 = new Card(Rank.valueOf(1), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(2), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(4), Suit.CLUBS);
    Card c5 = new Card(Rank.valueOf(5), Suit.CLUBS);
    Card c6 = new Card(Rank.valueOf(13), Suit.CLUBS);
    Card c7 = new Card(Rank.valueOf(10), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5,  c6, c7));
    modifyDeckMulti(customDeck);
    controller.playGame(new MultiPyramidSolitaire(), customDeck,
        false, 2, 5);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  K♣  10♣\n"
        + "Draw: 6♣, 7♣, 8♣, 9♣, J♣\n"
        + "Score: 38\n"
        + "  A♣  2♣  3♣\n"
        + "4♣  5♣  .   10♣\n"
        + "Draw: 6♣, 7♣, 8♣, 9♣, J♣\n"
        + "Score: 25\n"
        + "Invalid move. Play again. Cards cannot be removed yet.\n", out.toString());
  }

  @Test
  public void testRemove2RelaxedCovered() {
    this.in = new StringReader("rm2 2 1 1 1");
    Card c1 = new Card(Rank.valueOf(1), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(12), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(10), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
    modifyDeck(customDeck);
    initData();
    controller.playGame(rps1, customDeck, false, 2, 1);
    assertEquals("  A♣\n"
        + "Q♣  3♣\n"
        + "Draw:  10♣\n"
        + "Score: 16\n"
        + "Invalid move. Play again. Cards cannot be removed yet.\n", out.toString());
  }

  @Test
  public void testRemove2RelaxedNot13() {
    this.in = new StringReader("rm2 2 1 1 1");
    Card c1 = new Card(Rank.valueOf(1), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(11), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(10), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
    modifyDeck(customDeck);
    initData();
    controller.playGame(rps1, customDeck, false, 2, 1);
    assertEquals("  A♣\n"
        + "J♣  3♣\n"
        + "Draw:  10♣\n"
        + "Score: 15\n"
        + "Invalid move. Play again. Cards cannot be removed yet.\n", out.toString());
  }

  @Test
  public void testRemove2RelaxedSuccessController() {
    this.in = new StringReader("rmwd 1 2 2 rm2 2 1 1 1");
    Card c1 = new Card(Rank.valueOf(1), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(12), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(10), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
    modifyDeck(customDeck);
    initData();
    controller.playGame(rps1, customDeck, false, 2, 1);
    assertEquals("  A♣\n"
        + "Q♣  3♣\n"
        + "Draw:  10♣\n"
        + "Score: 16\n"
        + "  A♣\n"
        + "Q♣  .\n"
        + "Draw: 2♣\n"
        + "Score: 13\n"
        + "You win!", out.toString());
  }

  @Test
  public void testRemove2RelaxedCoveredController() {
    this.in = new StringReader("rm2 2 1 1 1");
    Card c1 = new Card(Rank.valueOf(1), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(12), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(10), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
    modifyDeck(customDeck);
    initData();
    controller.playGame(rps1, customDeck, false, 2, 1);
    assertEquals("  A♣\n"
        + "Q♣  3♣\n"
        + "Draw:  10♣\n"
        + "Score: 16\n"
        + "Invalid move. Play again. Cards cannot be removed yet.\n", out.toString());
  }

  @Test
  public void testRemove2RelaxedNot13Controller() {
    this.in = new StringReader("rmwd 1 2 2 rm2 2 1 1 1");
    Card c1 = new Card(Rank.valueOf(1), Suit.CLUBS);
    Card c2 = new Card(Rank.valueOf(11), Suit.CLUBS);
    Card c3 = new Card(Rank.valueOf(3), Suit.CLUBS);
    Card c4 = new Card(Rank.valueOf(10), Suit.CLUBS);
    List<Card> customDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
    modifyDeck(customDeck);
    initData();
    controller.playGame(rps1, customDeck, false, 2, 1);
    assertEquals("  A♣\n"
        + "J♣  3♣\n"
        + "Draw:  10♣\n"
        + "Score: 15\n"
        + "  A♣\n"
        + "J♣  .\n"
        + "Draw: 2♣\n"
        + "Score: 12\n"
        + "Invalid move. Play again. Cards cannot be removed yet.\n", out.toString());
  }

}
