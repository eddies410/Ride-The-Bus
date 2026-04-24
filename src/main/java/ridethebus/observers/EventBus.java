package ridethebus.observers;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton EventBus that broadcasts game events to all registered observers.
 * UI components register themselves as observers and react to events.
 * Must detach observers when they are no longer needed to prevent memory leaks.
 */
public class EventBus implements IGameSubject {

    private static final EventBus INSTANCE = new EventBus();
    private final List<IGameObserver> observers = new ArrayList<>();

    private EventBus() {
    }

    public static EventBus getInstance() {
        return INSTANCE;
    }

    @Override
    public void attach(IGameObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(IGameObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(GameEvent event) {
        for (IGameObserver observer : observers) {
            observer.onGameEvent(event);
        }
    }

    /**
     * Convenience method for posting events without creating a GameEvent manually.
     */
    public void post(GameEvent.Type type, String message) {
        notifyObservers(new GameEvent(type, message));
    }
}