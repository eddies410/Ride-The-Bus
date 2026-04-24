package ridethebus.observers;

/**
 * Subject interface for the Observer pattern.
 * Any class that broadcasts game events should implement this.
 */
public interface IGameSubject {
    void attach(IGameObserver observer);
    void detach(IGameObserver observer);
    void notifyObservers(GameEvent event);
}