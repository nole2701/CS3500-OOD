package cs3500.pyramidsolitaire.model.hw02;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a Card. Each card has a rank and a suit.
 */
public class Card {

  /**
   * Enumerates the card rank with their numerical value.
   */
  public enum Rank {
    ACE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13);

    private final int rankValue;
    private static final Map<Integer, Rank> map = new HashMap<>();

    /**
     * Constructs the Rank class for Card.
     * @param rankValue integer representing the rank's value.
     */
    Rank(int rankValue) {
      this.rankValue = rankValue;
    }

    static {
      for (Rank rankValue : Rank.values()) {
        map.put(rankValue.printRank(), rankValue);
      }
    }

    /**
     * Prints the rank as an int.
     * @return an int for the rank.
     */
    public int printRank() {
      return this.rankValue;
    }

    /**
     * Gets the enum from the integer given.
     * @param target the targeted rankValue of the enum.
     * @return returns the enum itself.
     */
    public static Rank valueOf(int target) {
      return map.get(target);
    }

  }

  /**
   * Enumerates the card suit with the symbol.
   */
  public enum Suit {
    CLUBS("♣"),
    DIAMONDS("♦"),
    HEARTS("♥"),
    SPADES("♠");


    private final String suitValue;

    /**
     * Constructs the Suit class for Card.
     * @param suitValue String with the suit symbol.
     */
    Suit(String suitValue) {
      this.suitValue = suitValue;
    }

    /**
     * Prints the suit symbol as a String.
     * @return a String for the suit symbol.
     */
    public String printSuit() {
      return this.suitValue;
    }

  }


  private final Rank rank;
  private final Suit suit;

  /**
   * Constructs a card with the given rank and suit.
   * @param rank rank of the card. Between 1 and 13.
   * @param suit suit of the card. Spades/Clubs/Hearts/Diamonds.
   */
  public Card(Rank rank, Suit suit) throws IllegalArgumentException {
    this.rank = rank;
    this.suit = suit;

    if (rank == null) {
      throw new IllegalArgumentException("Rank is null!");
    }
    if (suit == null) {
      throw new IllegalArgumentException("Suit is null!");
    }

    if (rank.rankValue > 13 || rank.rankValue < 1) {
      throw new IllegalArgumentException("Rank value must be between 1 and 13");
    }
    if (!suit.suitValue.equals("♣") && !suit.suitValue.equals("♠") && !suit.suitValue.equals("♥")
        && !suit.suitValue.equals("♦")) {
      throw new IllegalArgumentException("Suit must be either Hearts, Diamonds, Spades, or Clubs");
    }
  }

  /**
   * Gets the rank of this card.
   * @return int of the card rank.
   */
  public int getRank() {
    return rank.printRank();
  }

  /**
   * Gets the suit of this card.
   * @return suit symbol of this card.
   */
  public String getSuit() {
    return suit.printSuit();
  }

  /**
   * Clones the card.
   */
  @Override
  public Card clone() {
    return new Card(rank, suit);
  }

  /**
   * .toString() override for Cards.
   * @return Cards represented like "4♦" for 4 of Diamonds.
   */
  @Override
  public String toString() {
    String result = "";
    if (getRank() == 11) {
      result = "J" + suit.printSuit();
    } else if (getRank() == 12) {
      result = "Q" + suit.printSuit();
    } else if (getRank() == 13) {
      result = "K" + suit.printSuit();
    } else if (getRank() == 1) {
      result = "A" + suit.printSuit();
    } else {
      result = rank.printRank() + suit.printSuit();
    }
    return result;
  }

  /**
   * .equals() override for Cards.
   * Checks if the suit and rank are equal.
   * If other is null or of a different class, returns false.
   * @param other Object to compare with.
   * @return true or false if equal or not.
   */
  @Override
  public boolean equals(Object other) {
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    Card c = (Card)other;
    return c.getRank() == this.getRank() && c.getSuit().equals(this.getSuit());
  }

  /**
   * hashCode() override for Cards.
   * @return custom hashCode().
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + rank.printRank();
    result = prime * result + ((suit == null) ? 0 : suit.hashCode());
    return result;
  }

}
