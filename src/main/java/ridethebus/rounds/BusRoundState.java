package ridethebus.rounds;

import ridethebus.game.Game;

/**
 * State representing the bus round phase of the game.
 * Finds the highest scoring player and makes them ride the bus.
 * This is the final state -- returns null for nextState to end the game.
 */
public class BusRoundState implements IGameState {

    private final Game game;
    private BusRound busRound;
    private boolean complete = false;

    public BusRoundState(Game game) {
        this.game = game;
    }

    @Override
    public void start() {
        complete = false;
        busRound = new BusRound(
                game.getScoringStrategy(),
                game.getDeck(),
                game.getHighestScorer()
        );
        busRound.start();
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
        return null;
    }

    @Override
    public String getStateName() {
        return "Bus Round";
    }

    public BusRound getBusRound() {
        return busRound;
    }
}