package ridethebus.rounds;

import ridethebus.cards.ICard;
import ridethebus.cards.IDeck;
import ridethebus.characters.IPlayer;
import ridethebus.game.IGuess;
import ridethebus.scoring.IScoringStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the bus round for the highest scoring player.
 * The player must correctly guess all 4 questions in a row.
 * A wrong guess adds points and resets the sequence back to question 1.
 * The round only ends when all 4 are guessed correctly in a row.
 */
public class BusRound implements IRound {

    private static final int TOTAL_QUESTIONS = 4;

    private final IScoringStrategy scoringStrategy;
    private final IDeck deck;
    private final IPlayer rider;
    private final List<ICard> currentSequence = new ArrayList<>();
    private int currentQuestion = 0;
    private int totalAttempts = 0;
    private boolean isComplete = false;

    public BusRound(IScoringStrategy scoringStrategy, IDeck deck, IPlayer rider) {
        this.scoringStrategy = scoringStrategy;
        this.deck = deck;
        this.rider = rider;
    }

    @Override
    public void start() {
        currentQuestion = 0;
        totalAttempts = 0;
        isComplete = false;
        currentSequence.clear();
    }

    @Override
    public void end() {
        isComplete = true;
    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    /**
     * Processes a guess for the current question.
     * If correct, advances to the next question.
     * If wrong, adds points and resets the sequence to question 1.
     * Returns the card that was dealt so the UI can display it.
     */
    public ICard processGuess(IGuess guess) {
        ICard dealtCard = deck.deal();
        currentSequence.add(dealtCard);
        totalAttempts++;

        if (!guess.isCorrect(dealtCard)) {
            applyPenalty();
            resetSequence();
        } else {
            currentQuestion++;
            if (currentQuestion >= TOTAL_QUESTIONS) {
                end();
            }
        }

        return dealtCard;
    }

    private void applyPenalty() {
        int points = switch (currentQuestion) {
            case 0 -> scoringStrategy.pointsForWrongColorGuess();
            case 1 -> scoringStrategy.pointsForWrongHigherLowerGuess();
            case 2 -> scoringStrategy.pointsForWrongInsideOutsideGuess();
            case 3 -> scoringStrategy.pointsForWrongSuitGuess();
            default -> 0;
        };
        rider.addPoints(points + scoringStrategy.pointsForWrongBusGuess());
    }

    private void resetSequence() {
        currentQuestion = 0;
        currentSequence.clear();
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public IPlayer getRider() {
        return rider;
    }

    public List<ICard> getCurrentSequence() {
        return List.copyOf(currentSequence);
    }
}