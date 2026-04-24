package ridethebus.game;

import ridethebus.cards.ICard;

/**
 * Represents a higher or lower guess.
 * Second question in the guessing round.
 * Compares the new card value to the first card dealt.
 */
public class HigherLowerGuess implements IGuess {

    public static final String HIGHER = "Higher";
    public static final String LOWER = "Lower";

    private final String guess;
    private final ICard firstCard;

    public HigherLowerGuess(String guess, ICard firstCard) {
        if (!guess.equals(HIGHER) && !guess.equals(LOWER)) {
            throw new IllegalArgumentException("Guess must be Higher or Lower");
        }
        this.guess = guess;
        this.firstCard = firstCard;
    }

    @Override
    public boolean isCorrect(ICard card) {
        if (guess.equals(HIGHER)) {
            return card.getValue() > firstCard.getValue();
        }
        return card.getValue() < firstCard.getValue();
    }

    @Override
    public String getGuessDescription() {
        return "Higher or Lower: " + guess + " than " + firstCard.getDisplayName();
    }
}