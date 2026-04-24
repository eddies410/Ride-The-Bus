package ridethebus.game;

import ridethebus.cards.Card;
import ridethebus.cards.ICard;

/**
 * Represents a suit guess.
 * Fourth and final question in the guessing round.
 */
public class SuitGuess implements IGuess {

    private final String guess;

    public SuitGuess(String guess) {
        if (!guess.equals(Card.HEARTS) && !guess.equals(Card.DIAMONDS)
                && !guess.equals(Card.CLUBS) && !guess.equals(Card.SPADES)) {
            throw new IllegalArgumentException("Guess must be a valid suit");
        }
        this.guess = guess;
    }

    @Override
    public boolean isCorrect(ICard card) {
        return card.getSuit().equals(guess);
    }

    @Override
    public String getGuessDescription() {
        return "Suit: " + guess;
    }
}