package ridethebus.cards;

public class DeckFactory {

    public static IDeck createDeck(String type) {
        return switch (type) {
            case "joker" -> createJokerDeck();
            default -> createStandardDeck();
        };
    }

    public static IDeck createDeck() {
        return createStandardDeck();
    }

    private static IDeck createStandardDeck() {
        Deck deck = new Deck();
        return deck;
    }

    private static IDeck createJokerDeck() {
        Deck deck = new Deck();
        deck.addCard(new Joker());
        deck.addCard(new Joker());
        deck.shuffle();
        return deck;
    }
}