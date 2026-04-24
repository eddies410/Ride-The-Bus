//package ridethebus.observers;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import java.util.ArrayList;
//import java.util.List;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class EventBusTest {
//
//    // Test observer that collects events
//    private final List<GameEvent> receivedEvents = new ArrayList<>();
//    private final IGameObserver testObserver = event -> receivedEvents.add(event);
//
//    @AfterEach
//    void detachObserver() {
//        EventBus.getInstance().detach(testObserver);
//        receivedEvents.clear();
//    }
//
//    @Test
//    void observerReceivesPostedEvent() {
//        EventBus.getInstance().attach(testObserver);
//        EventBus.getInstance().post(GameEvent.Type.CARD_DEALT, "Ace of Spades");
//        assertEquals(1, receivedEvents.size());
//    }
//
//    @Test
//    void observerReceivesCorrectEventType() {
//        EventBus.getInstance().attach(testObserver);
//        EventBus.getInstance().post(GameEvent.Type.WRONG_GUESS, "Wrong color guess");
//        assertEquals(GameEvent.Type.WRONG_GUESS, receivedEvents.get(0).getType());
//    }
//
//    @Test
//    void detachedObserverDoesNotReceiveEvents() {
//        EventBus.getInstance().attach(testObserver);
//        EventBus.getInstance().detach(testObserver);
//        EventBus.getInstance().post(GameEvent.Type.CARD_DEALT, "Ace of Spades");
//        assertTrue(receivedEvents.isEmpty());
//    }
//
//    @Test
//    void multipleObserversAllReceiveEvents() {
//        List<GameEvent> secondObserverEvents = new ArrayList<>();
//        IGameObserver secondObserver = event -> secondObserverEvents.add(event);
//
//        EventBus.getInstance().attach(testObserver);
//        EventBus.getInstance().attach(secondObserver);
//        EventBus.getInstance().post(GameEvent.Type.SCORE_UPDATED, "Score changed");
//
//        assertEquals(1, receivedEvents.size());
//        assertEquals(1, secondObserverEvents.size());
//
//        EventBus.getInstance().detach(secondObserver);
//    }
//}