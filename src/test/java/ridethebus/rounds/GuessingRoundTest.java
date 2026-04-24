package ridethebus.rounds;

import org.junit.jupiter.api.Test;
import ridethebus.cards.Card;
import ridethebus.cards.ICard;
import ridethebus.cards.IDeck;
import ridethebus.characters.HumanPlayer;
import ridethebus.characters.IPlayer;
import ridethebus.game.*;
import ridethebus.scoring.AdultScoringStrategy;
import ridethebus.scoring.FamilyScoringStrategy;
import ridethebus.scoring.IScoringStrategy;
import static org.junit.jupiter.api.Assertions.*;

public class GuessingRoundTest {

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
    void correctColorGuessAddsNoPoints() {
        ICard redCard = new Card(Card.HEARTS, 5);
        IDeck deck = fixedDeck(redCard);
        IPlayer player = new HumanPlayer("Alice");
        GuessingRound round = new GuessingRound(new FamilyScoringStrategy(), deck);

        round.start();
        round.processGuess(player, new RedBlackGuess(Card.RED));

        assertEquals(0, player.getScore());
    }

    @Test
    void wrongColorGuessAddsPoints() {
        ICard redCard = new Card(Card.HEARTS, 5);
        IDeck deck = fixedDeck(redCard);
        IPlayer player = new HumanPlayer("Alice");
        GuessingRound round = new GuessingRound(new FamilyScoringStrategy(), deck);

        round.start();
        round.processGuess(player, new RedBlackGuess(Card.BLACK));

        assertTrue(player.getScore() > 0);
    }

    @Test
    void roundIsCompleteAfterFourQuestions() {
        IDeck deck = fixedDeck(
                new Card(Card.HEARTS, 5),
                new Card(Card.SPADES, 8),
                new Card(Card.DIAMONDS, 3),
                new Card(Card.CLUBS, 7)
        );
        IPlayer player = new HumanPlayer("Alice");
        GuessingRound round = new GuessingRound(new FamilyScoringStrategy(), deck);

        round.start();
        round.processGuess(player, new RedBlackGuess(Card.RED));
        round.processGuess(player, new HigherLowerGuess(HigherLowerGuess.HIGHER,
                new Card(Card.HEARTS, 5)));
        round.processGuess(player, new InsideOutsideGuess(InsideOutsideGuess.INSIDE,
                new Card(Card.HEARTS, 5), new Card(Card.SPADES, 8)));
        round.processGuess(player, new SuitGuess(Card.CLUBS));

        assertTrue(round.isComplete());
    }

    @Test
    void wrongColorPenaltyDiffersBasedOnStrategy() {
        ICard redCard = new Card(Card.HEARTS, 5);
        IPlayer familyPlayer = new HumanPlayer("Alice");
        IPlayer adultPlayer = new HumanPlayer("Bob");

        GuessingRound familyRound = new GuessingRound(
                new FamilyScoringStrategy(), fixedDeck(redCard));
        GuessingRound adultRound = new GuessingRound(
                new AdultScoringStrategy(), fixedDeck(new Card(Card.HEARTS, 5)));

        familyRound.start();
        adultRound.start();
        familyRound.processGuess(familyPlayer, new RedBlackGuess(Card.BLACK));
        adultRound.processGuess(adultPlayer, new RedBlackGuess(Card.BLACK));

        assertNotEquals(familyPlayer.getScore(), adultPlayer.getScore());
    }

    @Test
    void higherGuessCorrectForHigherCard() {
        ICard firstCard = new Card(Card.HEARTS, 5);
        ICard secondCard = new Card(Card.SPADES, 9);
        IGuess guess = new HigherLowerGuess(HigherLowerGuess.HIGHER, firstCard);
        assertTrue(guess.isCorrect(secondCard));
    }

    @Test
    void insideGuessCorrectWhenCardIsInRange() {
        ICard firstCard = new Card(Card.HEARTS, 3);
        ICard secondCard = new Card(Card.SPADES, 9);
        ICard thirdCard = new Card(Card.DIAMONDS, 6);
        IGuess guess = new InsideOutsideGuess(
                InsideOutsideGuess.INSIDE, firstCard, secondCard);
        assertTrue(guess.isCorrect(thirdCard));
    }

    @Test
    void suitGuessCorrectForMatchingSuit() {
        ICard card = new Card(Card.HEARTS, 5);
        IGuess guess = new SuitGuess(Card.HEARTS);
        assertTrue(guess.isCorrect(card));
    }
}