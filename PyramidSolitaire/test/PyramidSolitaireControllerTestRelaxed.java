import static org.junit.Assert.assertEquals;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import java.io.StringReader;
import org.junit.Test;

/**
 * Tests the basic rules on a relaxed game through a controller.
 */
public class PyramidSolitaireControllerTestRelaxed {

  RelaxedPyramidSolitaire rps1;
  PyramidSolitaireTextualController controller;
  StringReader in;
  StringBuilder out;

  protected void initData() {
    this.rps1 = new RelaxedPyramidSolitaire();
    this.out = new StringBuilder();
    this.controller = new PyramidSolitaireTextualController(in, out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    this.in = new StringReader("rm1 3 3");
    initData();
    controller = new PyramidSolitaireTextualController(in, null);
    controller.playGame(rps1, rps1.getDeck(), false, 5, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullReadable() {
    this.in = new StringReader("rm1 3 3");
    initData();
    controller = new PyramidSolitaireTextualController(null, out);
    controller.playGame(rps1, rps1.getDeck(), false, 5, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTooManyRows() {
    this.in = new StringReader("rm1 3 3");
    initData();
    controller = new PyramidSolitaireTextualController(null, out);
    controller.playGame(rps1, rps1.getDeck(), false, 99, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullDeck() {
    this.in = new StringReader("rm1 3 3");
    initData();
    controller = new PyramidSolitaireTextualController(null, out);
    controller.playGame(rps1, null, false, 5, 3);
  }

  @Test
  public void testRemove1IllegalNotKing() {
    this.in = new StringReader("rm1 2 2");
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 2, 1);
    assertEquals("  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n"
        + "Invalid move. Play again. Card is not a King.\n", out.toString());
  }

  @Test
  public void testRemove1IllegalCovered() {
    this.in = new StringReader("rm1 1 1");
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 2, 1);
    assertEquals("  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n"
        + "Invalid move. Play again. Card cannot be removed yet.\n", out.toString());
  }

  @Test
  public void testRemove1IllegalOutOfBounds() {
    this.in = new StringReader("rm1 5 1");
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 2, 1);
    assertEquals("  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n"
        + "Invalid move. Play again. Target is out of bounds.\n", out.toString());
  }

  @Test
  public void testRemove1Success() {
    this.in = new StringReader("rm1 5 3");
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 5, 1);
    assertEquals("        A♣\n"
        + "      2♣  3♣\n"
        + "    4♣  5♣  6♣\n"
        + "  7♣  8♣  9♣  10♣\n"
        + "J♣  Q♣  K♣  A♦  2♦\n"
        + "Draw: 3♦\n"
        + "Score: 94\n"
        + "        A♣\n"
        + "      2♣  3♣\n"
        + "    4♣  5♣  6♣\n"
        + "  7♣  8♣  9♣  10♣\n"
        + "J♣  Q♣  .   A♦  2♦\n"
        + "Draw: 3♦\n"
        + "Score: 81\n", out.toString());
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
  public void testRemoveDrawIllegalNot13() {
    this.in = new StringReader("rmwd 1 2 1");
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 2, 1);
    assertEquals("  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n"
        + "Invalid move. Play again. Cards don't add up to 13.\n", out.toString());
  }

  @Test
  public void testRemoveDrawIllegalCovered() {
    this.in = new StringReader("rmwd 1 1 1");
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 2, 1);
    assertEquals("  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n"
        + "Invalid move. Play again. Cards cannot be removed yet.\n", out.toString());
  }

  @Test
  public void testRemoveDrawIllegalOutOfBounds() {
    this.in = new StringReader("rmwd 1 5 1");
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 2, 1);
    assertEquals("  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n"
        + "Invalid move. Play again. Target is out of bounds.\n", out.toString());
  }

  @Test
  public void testRemoveDrawIllegalInvalidDrawIndex() {
    this.in = new StringReader("rmwd 5 2 1");
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 2, 1);
    assertEquals("  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n"
        + "Invalid move. Play again. drawIndex is out of bounds.\n", out.toString());
  }

  @Test
  public void testRemoveDrawSuccess() {
    this.in = new StringReader("rmwd 1 3 3");
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 3, 1);
    assertEquals("    A♣\n"
        + "  2♣  3♣\n"
        + "4♣  5♣  6♣\n"
        + "Draw: 7♣\n"
        + "Score: 21\n"
        + "    A♣\n"
        + "  2♣  3♣\n"
        + "4♣  5♣  .\n"
        + "Draw: 8♣\n"
        + "Score: 15\n", out.toString());
  }

  @Test
  public void testDiscardDrawIllegalInvalidDrawIndex() {
    this.in = new StringReader("dd 5");
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 2, 1);
    assertEquals("  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n"
        + "Invalid move. Play again. Invalid Draw Index.\n", out.toString());
  }

  @Test
  public void testDiscardDrawIllegalNoDrawCards() {
    this.in = new StringReader("dd 1");
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 5, 0);
    assertEquals("        A♣\n"
        + "      2♣  3♣\n"
        + "    4♣  5♣  6♣\n"
        + "  7♣  8♣  9♣  10♣\n"
        + "J♣  Q♣  K♣  A♦  2♦\n"
        + "Draw:\n"
        + "Score: 94\n"
        + "Invalid move. Play again. Invalid Draw Index.\n", out.toString());
  }

  @Test
  public void testDiscardDrawSuccess() {
    this.in = new StringReader("dd 1");
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 2, 1);
    assertEquals("  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n"
        + "  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 5♣\n"
        + "Score: 6\n", out.toString());
  }

  @Test
  public void testUnknownCommand() {
    this.in = new StringReader("bad haha dd 1");
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 2, 1);
    assertEquals("  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n"
        + "Invalid command. Fix invalid input \"bad\" and continue.\n"
        + "Invalid command. Fix invalid input \"haha\" and continue.\n"
        + "  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 5♣\n"
        + "Score: 6\n", out.toString());
  }

  @Test
  public void testInvalidCommandVariable() {
    this.in = new StringReader("dd a a a 1");
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 2, 1);
    assertEquals("  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n"
        + "Input cannot be read. Fix invalid input \"a\" and continue.\n"
        + "Input cannot be read. Fix invalid input \"a\" and continue.\n"
        + "Input cannot be read. Fix invalid input \"a\" and continue.\n"
        + "  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 5♣\n"
        + "Score: 6\n", out.toString());
  }

  @Test
  public void testQuitCleanLowercase() {
    this.in = new StringReader("q asdf");
    //Should not process the "asdf" since the program should exit.
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 2, 1);
    assertEquals("  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n", out.toString());
  }

  @Test
  public void testQuitCleanUppercase() {
    this.in = new StringReader("Q asdf");
    //Should not process the "asdf" since the program should exit.
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 2, 1);
    assertEquals("  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n", out.toString());
  }

  @Test
  public void testQuitMidCommand() {
    this.in = new StringReader("dd q asdf");
    //Should not process the "asdf" since the program should exit.
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 2, 1);
    assertEquals("  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n", out.toString());
  }

  @Test
  public void testQuitMidPlay() {
    this.in = new StringReader("dd 1 dd 1 q asdf");
    //Should not process the "asdf" since the program should exit.
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 2, 1);
    assertEquals("  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣\n"
        + "Score: 6\n"
        + "  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 5♣\n"
        + "Score: 6\n"
        + "  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 6♣\n"
        + "Score: 6\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 6♣\n"
        + "Score: 6\n", out.toString());
  }

  @Test
  public void testGameOverLose() {
    this.in = new StringReader("rm1 5 3 rm2 5 1 5 5 rm2 5 2 5 4 asdf");
    //Should not process the "asdf" since the program should exit.
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 5, 0);
    assertEquals("        A♣\n"
        + "      2♣  3♣\n"
        + "    4♣  5♣  6♣\n"
        + "  7♣  8♣  9♣  10♣\n"
        + "J♣  Q♣  K♣  A♦  2♦\n"
        + "Draw:\n"
        + "Score: 94\n"
        + "        A♣\n"
        + "      2♣  3♣\n"
        + "    4♣  5♣  6♣\n"
        + "  7♣  8♣  9♣  10♣\n"
        + "J♣  Q♣  .   A♦  2♦\n"
        + "Draw:\n"
        + "Score: 81\n"
        + "        A♣\n"
        + "      2♣  3♣\n"
        + "    4♣  5♣  6♣\n"
        + "  7♣  8♣  9♣  10♣\n"
        + ".   Q♣  .   A♦  .\n"
        + "Draw:\n"
        + "Score: 68\n"
        + "Game over. Score: 55", out.toString());
  }

  @Test
  public void testGameOverWin() {
    this.in = new StringReader("rmwd 11 1 1 asdf");
    //Should not process the "asdf" since the program should exit.
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 1, 11);
    assertEquals("A♣\n"
        + "Draw: 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣\n"
        + "Score: 1\n"
        + "You win!", out.toString());
  }

  @Test
  public void testGameOverWinWithBadInput() {
    this.in = new StringReader("asdf dd 123 rmwd 11 1 1 asdf");
    //Should not process the "asdf" since the program should exit.
    initData();
    controller.playGame(rps1, rps1.getDeck(), false, 1, 11);
    assertEquals("A♣\n"
        + "Draw: 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣\n"
        + "Score: 1\n"
        + "Invalid command. Fix invalid input \"asdf\" and continue.\n"
        + "Invalid move. Play again. Invalid Draw Index.\n"
        + "You win!", out.toString());
  }

}
