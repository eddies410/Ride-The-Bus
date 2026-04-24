package ridethebus.rounds;

import ridethebus.cards.ICard;
import ridethebus.cards.IDeck;
import ridethebus.characters.IPlayer;
import ridethebus.game.IGuess;
import ridethebus.scoring.IScoringStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the guessing round for a single player.
 * Each player goes through 4 questions in sequence.
 * Wrong guesses add points to the player's score.
 */
public class GuessingRound implements IRound {

    private static final int TOTAL_QUESTIONS = 4;

    private final IScoringStrategy scoringStrategy;
    private final IDeck deck;
    private final List<ICard> dealtCards = new ArrayList<>();
    private int currentQuestion = 0;
    private boolean isComplete = false;

    public GuessingRound(IScoringStrategy scoringStrategy, IDeck deck) {
        this.scoringStrategy = scoringStrategy;
        this.deck = deck;
    }

    @Override
    public void start() {
        currentQuestion = 0;
        isComplete = false;
        dealtCards.clear();
    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public void end() {
        isComplete = true;
    }

    /**
     * Processes a guess for the current question.
     * Deals a card, checks the guess, and applies penalty if wrong.
     * Returns the card that was dealt so the UI can display it.
     */
    public ICard processGuess(IPlayer player, IGuess guess) {
        ICard dealtCard = deck.deal();
        dealtCards.add(dealtCard);

        if (!guess.isCorrect(dealtCard)) {
            applyPenalty(player);
        }

        currentQuestion++;
        if (currentQuestion >= TOTAL_QUESTIONS) {
            end();
        }

        return dealtCard;
    }

    private void applyPenalty(IPlayer player) {
        int points = switch (currentQuestion) {
            case 0 -> scoringStrategy.pointsForWrongColorGuess();
            case 1 -> scoringStrategy.pointsForWrongHigherLowerGuess();
            case 2 -> scoringStrategy.pointsForWrongInsideOutsideGuess();
            case 3 -> scoringStrategy.pointsForWrongSuitGuess();
            default -> 0;
        };
        player.addPoints(points);
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public List<ICard> getDealtCards() {
        return List.copyOf(dealtCards);
    }
}