package ridethebus.observers;

/**
 * Represents a game event that is broadcast to all observers.
 * Contains the event type and an optional message for the UI to display.
 */
public class GameEvent {

    public enum Type {
        CARD_DEALT,
        CORRECT_GUESS,
        WRONG_GUESS,
        ROUND_STARTED,
        ROUND_ENDED,
        BUS_RESET,
        GAME_OVER,
        PLAYER_ADDED,
        SCORE_UPDATED
    }

    private final Type type;
    private final String message;

    public GameEvent(Type type, String message) {
        this.type = type;
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return type + ": " + message;
    }
}