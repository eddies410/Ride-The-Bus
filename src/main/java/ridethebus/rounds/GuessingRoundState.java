package ridethebus.rounds;

import ridethebus.game.Game;

/**
 * State representing the guessing round phase of the game.
 * Transitions to BusRoundState when complete.
 */
public class GuessingRoundState implements IGameState {

    private final Game game;
    private boolean complete = false;

    public GuessingRoundState(Game game) {
        this.game = game;
    }

    @Override
    public void start() {
        complete = false;
    }

    @Override
    public void end() {
        complete = true;
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public IGameState nextState() {
        return new BusRoundState(game);
    }

    @Override
    public String getStateName() {
        return "Guessing Round";
    }
}