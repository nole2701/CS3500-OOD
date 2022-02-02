import static org.junit.Assert.assertEquals;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;
import java.io.StringReader;
import org.junit.Test;

/**
 * Tests the basic rules on a multi-pyramid game through a controller.
 */
public class PyramidSolitaireControllerTestMulti {

  MultiPyramidSolitaire mps1;
  PyramidSolitaireTextualController controller;
  StringReader in;
  StringBuilder out;

  protected void initData() {
    this.mps1 = new MultiPyramidSolitaire();
    this.out = new StringBuilder();
    this.controller = new PyramidSolitaireTextualController(in, out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    this.in = new StringReader("rm1 3 3");
    initData();
    controller = new PyramidSolitaireTextualController(in, null);
    controller.playGame(mps1, mps1.getDeck(), false, 5, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullReadable() {
    this.in = new StringReader("rm1 3 3");
    initData();
    controller = new PyramidSolitaireTextualController(null, out);
    controller.playGame(mps1, mps1.getDeck(), false, 5, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTooManyRows() {
    this.in = new StringReader("rm1 3 3");
    initData();
    controller = new PyramidSolitaireTextualController(null, out);
    controller.playGame(mps1, mps1.getDeck(), false, 99, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullDeck() {
    this.in = new StringReader("rm1 3 3");
    initData();
    controller = new PyramidSolitaireTextualController(null, out);
    controller.playGame(mps1, null, false, 5, 3);
  }

  @Test
  public void testRender7Rows() {
    this.in = new StringReader("test");
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 7, 1);
    assertEquals(""
        + "            A♣  .   .   2♣  .   .   3♣\n"
        + "          4♣  5♣  .   6♣  7♣  .   8♣  9♣\n"
        + "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n"
        + "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
        + "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n"
        + "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n"
        + "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n"
        + "Draw: K♣\n"
        + "Score: 442\n"
        + "Invalid command. Fix invalid input \"test\" and continue.\n", out.toString());
  }


  @Test
  public void testRemove1IllegalNotKing() {
    this.in = new StringReader("rm1 2 2");
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 1);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n"
        + "Invalid move. Play again. Card is not a King.\n", out.toString());
  }

  @Test
  public void testRemove1IllegalCovered() {
    this.in = new StringReader("rm1 1 1");
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 1);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n"
        + "Invalid move. Play again. Card cannot be removed yet.\n", out.toString());
  }

  @Test
  public void testRemove1IllegalOutOfBounds() {
    this.in = new StringReader("rm1 123 1");
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 1);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n"
        + "Invalid move. Play again. Target is out of bounds.\n", out.toString());
  }

  @Test
  public void testRemove1Success() {
    this.in = new StringReader("rm1 5 2");
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 5, 1);
    assertEquals(""
        + "        A♣  .   2♣  .   3♣\n"
        + "      4♣  5♣  6♣  7♣  8♣  9♣\n"
        + "    10♣ J♣  Q♣  K♣  A♦  2♦  3♦\n"
        + "  4♦  5♦  6♦  7♦  8♦  9♦  10♦ J♦\n"
        + "Q♦  K♦  A♥  2♥  3♥  4♥  5♥  6♥  7♥\n"
        + "Draw: 8♥\n"
        + "Score: 210\n"
        + "        A♣  .   2♣  .   3♣\n"
        + "      4♣  5♣  6♣  7♣  8♣  9♣\n"
        + "    10♣ J♣  Q♣  K♣  A♦  2♦  3♦\n"
        + "  4♦  5♦  6♦  7♦  8♦  9♦  10♦ J♦\n"
        + "Q♦  .   A♥  2♥  3♥  4♥  5♥  6♥  7♥\n"
        + "Draw: 8♥\n"
        + "Score: 197\n", out.toString());
  }

  @Test
  public void testRemove2IllegalNot13() {
    this.in = new StringReader("rm2 2 1 2 2");
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 1);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n"
        + "Invalid move. Play again. Cards don't add up to 13.\n", out.toString());
  }

  @Test
  public void testRemove2IllegalCovered() {
    this.in = new StringReader("rm2 1 1 2 2");
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 1);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n"
        + "Invalid move. Play again. Cards cannot be removed yet.\n", out.toString());
  }

  @Test
  public void testRemove2IllegalOutOfBounds() {
    this.in = new StringReader("rm2 5 1 2 2");
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 1);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n"
        + "Invalid move. Play again. Target is out of bounds.\n", out.toString());
  }

  @Test
  public void testRemove2Success() {
    this.in = new StringReader("rm2 2 4 2 3");
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 1);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n"
        + "  A♣  2♣  3♣\n"
        + "4♣  5♣  .   .\n"
        + "Draw: 8♣\n"
        + "Score: 15\n", out.toString());
  }

  @Test
  public void testRemoveDrawIllegalNot13() {
    this.in = new StringReader("rmwd 1 2 1");
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 1);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n"
        + "Invalid move. Play again. Cards don't add up to 13.\n", out.toString());
  }

  @Test
  public void testRemoveDrawIllegalCovered() {
    this.in = new StringReader("rmwd 1 1 1");
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 1);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n"
        + "Invalid move. Play again. Cards cannot be removed yet.\n", out.toString());
  }

  @Test
  public void testRemoveDrawIllegalOutOfBounds() {
    this.in = new StringReader("rmwd 1 5 1");
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 1);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n"
        + "Invalid move. Play again. Target is out of bounds.\n", out.toString());
  }

  @Test
  public void testRemoveDrawIllegalInvalidDrawIndex() {
    this.in = new StringReader("rmwd 5 2 1");
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 1);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n"
        + "Invalid move. Play again. drawIndex is out of bounds.\n", out.toString());
  }

  @Test
  public void testRemoveDrawSuccess() {
    this.in = new StringReader("rmwd 2 2 1");
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 5);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣, 9♣, 10♣, J♣, Q♣\n"
        + "Score: 28\n"
        + "  A♣  2♣  3♣\n"
        + ".   5♣  6♣  7♣\n"
        + "Draw: 8♣, K♣, 10♣, J♣, Q♣\n"
        + "Score: 24\n", out.toString());
  }

  @Test
  public void testDiscardDrawIllegalInvalidDrawIndex() {
    this.in = new StringReader("dd 5");
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 1);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n"
        + "Invalid move. Play again. Invalid Draw Index.\n", out.toString());
  }

  @Test
  public void testDiscardDrawIllegalNoDrawCards() {
    this.in = new StringReader("dd 5");
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 0);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw:\n"
        + "Score: 28\n"
        + "Invalid move. Play again. Invalid Draw Index.\n", out.toString());
  }

  @Test
  public void testDiscardDrawSuccess() {
    this.in = new StringReader("dd 1");
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 1);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n"
        + "  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 9♣\n"
        + "Score: 28\n", out.toString());
  }

  @Test
  public void testUnknownCommand() {
    this.in = new StringReader("bad haha dd 1");
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 1);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n"
        + "Invalid command. Fix invalid input \"bad\" and continue.\n"
        + "Invalid command. Fix invalid input \"haha\" and continue.\n"
        + "  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 9♣\n"
        + "Score: 28\n", out.toString());
  }

  @Test
  public void testInvalidCommandVariable() {
    this.in = new StringReader("dd a a a 1");
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 1);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n"
        + "Input cannot be read. Fix invalid input \"a\" and continue.\n"
        + "Input cannot be read. Fix invalid input \"a\" and continue.\n"
        + "Input cannot be read. Fix invalid input \"a\" and continue.\n"
        + "  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 9♣\n"
        + "Score: 28\n", out.toString());
  }

  @Test
  public void testQuitCleanLowercase() {
    this.in = new StringReader("q asdf");
    //Should not process the "asdf" since the program should exit.
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 1);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n", out.toString());
  }

  @Test
  public void testQuitCleanUppercase() {
    this.in = new StringReader("Q asdf");
    //Should not process the "asdf" since the program should exit.
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 1);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n", out.toString());
  }

  @Test
  public void testQuitMidCommand() {
    this.in = new StringReader("dd q asdf");
    //Should not process the "asdf" since the program should exit.
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 1);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n", out.toString());
  }

  @Test
  public void testQuitMidPlay() {
    this.in = new StringReader("dd 1 dd 1 q asdf");
    //Should not process the "asdf" since the program should exit.
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 1);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣\n"
        + "Score: 28\n"
        + "  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw: 9♣\n"
        + "Score: 28\n"
        + "  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw:  10♣\n"
        + "Score: 28\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw:  10♣\n"
        + "Score: 28\n", out.toString());
  }

  @Test
  public void testGameOverLose() {
    this.in = new StringReader("rm2 2 4 2 3 asdf");
    //Should not process the "asdf" since the program should exit.
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 2, 0);
    assertEquals("  A♣  2♣  3♣\n"
        + "4♣  5♣  6♣  7♣\n"
        + "Draw:\n"
        + "Score: 28\n"
        + "Game over. Score: 15", out.toString());
  }

  @Test
  public void testGameOverWin() {
    this.in = new StringReader("rmwd 11 1 1 asdf");
    //Should not process the "asdf" since the program should exit.
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 1, 11);
    assertEquals("A♣\n"
        + "Draw: 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣\n"
        + "Score: 1\n"
        + "You win!", out.toString());
  }

  @Test
  public void testGameOverWinWithBadInput() {
    this.in = new StringReader("dwer dd 22 rmwd 11 1 1 asdf");
    //Should not process the "asdf" since the program should exit.
    initData();
    controller.playGame(mps1, mps1.getDeck(), false, 1, 11);
    assertEquals("A♣\n"
        + "Draw: 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣\n"
        + "Score: 1\n"
        + "Invalid command. Fix invalid input \"dwer\" and continue.\n"
        + "Invalid move. Play again. Invalid Draw Index.\n"
        + "You win!", out.toString());
  }

}
