package ridethebus.game;

import ridethebus.cards.Card;
import ridethebus.cards.ICard;

/**
 * Represents a red or black guess.
 * First question in the guessing round.
 */
public class RedBlackGuess implements IGuess {

    private final String guess;

    public RedBlackGuess(String guess) {
        if (!guess.equals(Card.RED) && !guess.equals(Card.BLACK)) {
            throw new IllegalArgumentException("Guess must be Red or Black");
        }
        this.guess = guess;
    }

    @Override
    public boolean isCorrect(ICard card) {
        return card.getColor().equals(guess);
    }

    @Override
    public String getGuessDescription() {
        return "Red or Black: " + guess;
    }
}