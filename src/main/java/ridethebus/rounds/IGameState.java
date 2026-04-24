package ridethebus.rounds;

/**
 * Represents a state in the game using the State pattern.
 * Each phase of the game is a different state with its own rules.
 * Transitions to the next state when the current phase is complete.
 */
public interface IGameState {
    void start();
    void end();
    boolean isComplete();
    IGameState nextState();
    String getStateName();
}