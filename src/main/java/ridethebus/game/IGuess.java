package ridethebus.game;

import ridethebus.cards.ICard;

/**
 * Represents a guess made by a player during the guessing round.
 */
public interface IGuess {
    boolean isCorrect(ICard card);
    String getGuessDescription();
}