import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testing the game factory to see if the create method properly makes the specified game type.
 * Also tests for error handling for a null game type.
 */
public class PyramidSolitaireCreatorTest {

  @Test
  public void testCreateBasic() {
    PyramidSolitaireModel<Card> game = PyramidSolitaireCreator.create(GameType.BASIC);
    assertEquals(game.getClass(), BasicPyramidSolitaire.class);
  }

  @Test
  public void testCreateBasicTypeOf() {
    PyramidSolitaireModel<Card> game = PyramidSolitaireCreator.create(GameType.typeOf("basic"));
    assertEquals(game.getClass(), BasicPyramidSolitaire.class);
  }

  @Test
  public void testCreateRelaxed() {
    PyramidSolitaireModel<Card> game = PyramidSolitaireCreator.create(GameType.RELAXED);
    assertEquals(game.getClass(), RelaxedPyramidSolitaire.class);
  }

  @Test
  public void testCreateRelaxedTypeOf() {
    PyramidSolitaireModel<Card> game = PyramidSolitaireCreator.create(GameType.typeOf("relaxed"));
    assertEquals(game.getClass(), RelaxedPyramidSolitaire.class);
  }

  @Test
  public void testCreateMulti() {
    PyramidSolitaireModel<Card> game = PyramidSolitaireCreator.create(GameType.MULTIPYRAMID);
    assertEquals(game.getClass(), MultiPyramidSolitaire.class);
  }

  @Test
  public void testCreateMultiTypeOf() {
    PyramidSolitaireModel<Card> game =
        PyramidSolitaireCreator.create(GameType.typeOf("multipyramid"));
    assertEquals(game.getClass(), MultiPyramidSolitaire.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateNullGameType() {
    PyramidSolitaireModel<Card> game = PyramidSolitaireCreator.create(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateInvalidGameType() {
    PyramidSolitaireModel<Card> game = PyramidSolitaireCreator.create(GameType.typeOf("test"));
  }
}
