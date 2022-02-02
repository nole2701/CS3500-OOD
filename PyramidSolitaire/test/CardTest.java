import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Card.Rank;
import cs3500.pyramidsolitaire.model.hw02.Card.Suit;
import org.junit.Test;

/**
 * Tests for cards.
 */
public class CardTest {

  Card card4D = new Card(Rank.FOUR, Suit.DIAMONDS);
  Card card3H = new Card(Rank.THREE, Suit.HEARTS);
  Card card4D2 = new Card(Rank.FOUR, Suit.DIAMONDS);
  Card card4DFromValues = new Card(Rank.valueOf(4), Suit.DIAMONDS);


  /**
   * inherently also tests printRank() inside Rank.
   */
  @Test
  public void testGetRank() {
    assertEquals(card4D.getRank(), 4);
  }

  /**
   * inherently also tests printSuit() inside Suit.
   */
  @Test
  public void testGetSuit() {
    assertEquals(card4D.getSuit(), "♦");
  }

  /**
   * Testing getRank() for card made from values.
   */
  @Test
  public void testGetRankFromValue() {
    assertEquals(card4DFromValues.getRank(), 4);
  }

  /**
   * testing toString() for Card.
   */
  @Test
  public void testToStringCard() {
    assertEquals(card4D.toString(), "4♦");
  }

  /**
   * testing .equals override for Card.
   */
  @Test
  public void testEqualCard() {
    assertEquals(card4D, card4D2);
  }

  @Test
  public void testNotEqualCard() {
    assertNotEquals(card4D, card3H);
  }

  @Test
  public void testEqualSameCard() {
    assertEquals(card4D, card4D);
  }

  @Test
  public void testEqualNullCard() {
    assertNotEquals(null, card4D);
  }

  /**
   * Testing hashCode() for Card.
   */
  @Test
  public void testHashCodeCardSame() {
    assertEquals(card4D.hashCode(), card4D2.hashCode());
  }

  @Test
  public void testHashCodeCardNotSame() {
    assertNotEquals(card4D.hashCode(), card3H.hashCode());
  }

}
