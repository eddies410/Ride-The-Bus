package ridethebus.rounds;

import org.junit.jupiter.api.Test;
import ridethebus.cards.Card;
import ridethebus.cards.ICard;
import ridethebus.cards.IDeck;
import ridethebus.characters.HumanPlayer;
import ridethebus.characters.IPlayer;
import ridethebus.game.*;
import ridethebus.scoring.FamilyScoringStrategy;
import static org.junit.jupiter.api.Assertions.*;

public class BusRoundTest {

    // Fixed deck that always deals cards in a known order for testing
    private IDeck fixedDeck(ICard... cards) {
        return new IDeck() {
            int index = 0;
            public ICard deal() { return cards[index++]; }
            public void shuffle() {}
            public int size() { return cards.length - index; }
            public boolean isEmpty() { return index >= cards.length; }
            public java.util.List<ICard> dealHand(int n) {
                java.util.List<ICard> hand = new java.util.ArrayList<>();
                for (int i = 0; i < n; i++) hand.add(deal());
                return hand;
            }
        };
    }

    @Test
    void busRoundStartsAtQuestionZero() {
        IPlayer rider = new HumanPlayer("Alice");
        BusRound round = new BusRound(
                new FamilyScoringStrategy(),
                fixedDeck(new Card(Card.HEARTS, 5)),
                rider
        );
        round.start();
        assertEquals(0, round.getCurrentQuestion());
    }

    @Test
    void correctGuessAdvancesQuestion() {
        ICard redCard = new Card(Card.HEARTS, 5);
        IPlayer rider = new HumanPlayer("Alice");
        BusRound round = new BusRound(
                new FamilyScoringStrategy(),
                fixedDeck(redCard),
                rider
        );
        round.start();
        round.processGuess(new RedBlackGuess(Card.RED));
        assertEquals(1, round.getCurrentQuestion());
    }

    @Test
    void wrongGuessResetsSequence() {
        ICard redCard = new Card(Card.HEARTS, 5);
        IPlayer rider = new HumanPlayer("Alice");
        BusRound round = new BusRound(
                new FamilyScoringStrategy(),
                fixedDeck(redCard),
                rider
        );
        round.start();
        round.processGuess(new RedBlackGuess(Card.BLACK));
        assertEquals(0, round.getCurrentQuestion());
    }

    @Test
    void wrongGuessAddsPoints() {
        ICard redCard = new Card(Card.HEARTS, 5);
        IPlayer rider = new HumanPlayer("Alice");
        BusRound round = new BusRound(
                new FamilyScoringStrategy(),
                fixedDeck(redCard),
                rider
        );
        round.start();
        round.processGuess(new RedBlackGuess(Card.BLACK));
        assertTrue(rider.getScore() > 0);
    }

    @Test
    void roundCompletesAfterFourCorrectGuesses() {
        IPlayer rider = new HumanPlayer("Alice");
        BusRound round = new BusRound(
                new FamilyScoringStrategy(),
                fixedDeck(
                        new Card(Card.HEARTS, 5),
                        new Card(Card.SPADES, 9),
                        new Card(Card.DIAMONDS, 7),
                        new Card(Card.CLUBS, 2)
                ),
                rider
        );
        round.start();
        round.processGuess(new RedBlackGuess(Card.RED));
        round.processGuess(new HigherLowerGuess(
                HigherLowerGuess.HIGHER, new Card(Card.HEARTS, 5)));
        round.processGuess(new InsideOutsideGuess(
                InsideOutsideGuess.INSIDE,
                new Card(Card.HEARTS, 5),
                new Card(Card.SPADES, 9)));
        round.processGuess(new SuitGuess(Card.CLUBS));
        assertTrue(round.isComplete());
    }

    @Test
    void totalAttemptsTrackedCorrectly() {
        IPlayer rider = new HumanPlayer("Alice");
        BusRound round = new BusRound(
                new FamilyScoringStrategy(),
                fixedDeck(
                        new Card(Card.HEARTS, 5),
                        new Card(Card.HEARTS, 7)
                ),
                rider
        );
        round.start();
        round.processGuess(new RedBlackGuess(Card.BLACK)); // wrong, resets
        round.processGuess(new RedBlackGuess(Card.RED));   // correct
        assertEquals(2, round.getTotalAttempts());
    }

    @Test
    void riderIsCorrectlyIdentified() {
        IPlayer rider = new HumanPlayer("Alice");
        BusRound round = new BusRound(
                new FamilyScoringStrategy(),
                fixedDeck(new Card(Card.HEARTS, 5)),
                rider
        );
        assertEquals("Alice", round.getRider().getName());
    }
}