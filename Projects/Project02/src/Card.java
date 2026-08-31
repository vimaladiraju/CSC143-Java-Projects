/**
 * Represents a single card in a card game
 *
 * @param number    card number, 1 = ace, 2 = two, ..., 11 = Jack, 12 = Queen, 13 = King;
 *                  must be in range 1 to 13 inclusive
 * @param suit      suit within the deck; must not be null
 */
public record Card(byte number, Suit suit) {

    /**
     * All possible suits in a deck of cards
     */
    public enum Suit {
        /** Spades suit */      Spades,
        /** Diamonds suit */    Diamonds,
        /** Club suit */        Clubs,
        /** Hearts suit */      Hearts}

    /**
     * constructor supplement; handle bad arguments
     * @param number    card number, 1 = ace, 2 = two, ..., 11 = Jack, 12 = Queen, 13 = King;
     *                  must be in range 1 to 13 inclusive
     * @param suit      suit within the deck; must not be null
     */
    public Card {
        if (number < 1 || number > 13) {
            throw new IllegalArgumentException("number must be in the range 1 to 13, inclusive");
        }
        if (suit == null) {
            throw new IllegalArgumentException("suit must not be null");
        }
    }

    /**
     * Returns a friendly name for the card, e.g., if number is 1, then "Ace" is returned
     * @return      friendly name for the card, e.g., "Ace" if the card's number is 1, "Two" if number is 2.
     */
    public String getCardName() {
        switch (number) {
            case  1 : return "Ace";
            case  2 : return "Two";
            case  3 : return "Three";
            case  4 : return "Four";
            case  5 : return "Five";
            case  6 : return "Six";
            case  7 : return "Seven";
            case  8 : return "Eight";
            case  9 : return "Nine";
            case 10 : return "Ten";
            case 11 : return "Jack";
            case 12 : return "Queen";
            default : return "King";
        }
    }

    /**
     * Constructs and returns a friendly name for the card and suit, e.g., "Ace of Spades"
     * @return      friendly name for the card and suit, e.g., "Ace of Spades"
     */
    @Override
    public String toString() {
        return getCardName() + " of " + suit;
    }

}
