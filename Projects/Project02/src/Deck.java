import java.util.Iterator;

/**
 * Represents a deck of cards, e.g., for a card game
 */
public class Deck implements Iterable<Card>, Cloneable {

    /**
     * list of cards in the deck
     */
    private CircularLinkedListInterface<Card> cards;

    /**
     * Constructor; creates the deck and populates it with 13 cards in each suit.
     * No Joker cards are included.  Deck ordering is NDO deck ordering (see
     * <a href="https://en.wikipedia.org/wiki/Standard_52-card_deck">Wikipedia, see New-deck order</a>).
     */
    public Deck() {
        cards = new CircularLinkedList<>();

        // First two suits are Ace to King order; second two are King to Ace ordering
        for (byte cardNum = 1; cardNum <= 13; cardNum++) {
            cards.add(new Card(cardNum, Card.Suit.Spades));
        }
        for (byte cardNum = 1; cardNum <= 13; cardNum++) {
            cards.add(new Card(cardNum, Card.Suit.Diamonds));
        }
        for (byte cardNum = 13; cardNum >= 1; cardNum--) {
            cards.add(new Card(cardNum, Card.Suit.Clubs));
        }
        for (byte cardNum = 13; cardNum >= 1; cardNum--) {
            cards.add(new Card(cardNum, Card.Suit.Hearts));
        }
    }

    /**
     * Retrieves the count of cards in the deck; this will diminish as cards are drawn during a game
     * @return  count of cards in the deck
     */
    public int getCardCount() {
        return cards.size();
    }

    /**
     * Draws a single card from the deck, removing it from the deck and returning it
     * @return      drawn card
     */
    public Card drawCard() {
        Card drawnCard = cards.get(0);
        cards.remove(0);
        return drawnCard;
    }

    /**
     * Returns a textual representation of all cards in the deck; may be useful for debugging purposes
     * @return      textual representation of the deck
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Deck with card count:");
        sb.append(cards.size());
        sb.append('\n');
        int cardIndex = 0;
        Iterator<Card> cardIterator = cards.iterator();
        while (cardIterator.hasNext() && cardIndex < cards.size()) {
            sb.append(cardIterator.next());
            sb.append('\n');
            cardIndex++;
        }
        return sb.toString();
    }

    /**
     * Creates and returns a shallow copy (deck is cloned but cards aren't) of the deck
     * @return      shallow copy of this deck (referring to the same cards in memory as did this deck)
     */
    @Override
    public Deck clone() {
        Deck newDeck;
        try {
            // this creates the cloned object, but it points at the same card list
            newDeck = (Deck) super.clone();
        } catch (CloneNotSupportedException e) {
            // To avoid this exception, this class implements the Cloneable interface
            throw new RuntimeException(e);
        }
        newDeck.cards = this.cards.clone();
        return newDeck;
    }

    /**
     * Shuffles (randomizes) the cards in the deck.
     */
    public void shuffle() {
        cards.shuffle();
    }

    /**
     * Retrieves an iterator over the cards in the deck.  Do not do other list operations like add or remove
     * from within an iterator loop; the results are not guaranteed to function as you might expect.
     *
     * @return a strongly typed iterator over the cards in the list
     */
    @Override
    public Iterator<Card> iterator() {
        return cards.iterator();
    }
}
