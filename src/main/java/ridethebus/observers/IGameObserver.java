package ridethebus.observers;

/**
 * Observer interface for game events.
 * Any UI component that needs to react to game state changes
 * should implement this interface.
 */
public interface IGameObserver {
    void onGameEvent(GameEvent event);
}