package ridethebus.game;

import ridethebus.cards.DeckFactory;
import ridethebus.cards.IDeck;
import ridethebus.characters.IPlayer;
import ridethebus.observers.EventBus;
import ridethebus.observers.GameEvent;
import ridethebus.rounds.GuessingRoundState;
import ridethebus.rounds.IGameState;
import ridethebus.scoring.IScoringStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Top level game class that manages players, rounds, and scoring.
 * Uses dependency injection for scoring strategy and deck.
 * Posts events to the EventBus when game state changes.
 */
public class Game implements IGame {

    private final IScoringStrategy scoringStrategy;
    private final IDeck deck;
    private final List<IPlayer> players = new ArrayList<>();
    private IGameState currentState;
    private boolean gameOver = false;

    public Game(IScoringStrategy scoringStrategy) {
        this.scoringStrategy = scoringStrategy;
        this.deck = DeckFactory.createDeck();
    }

    @Override
    public void start() {
        if (players.isEmpty()) {
            throw new IllegalStateException("Cannot start a game with no players");
        }
        dealInitialHands();
        currentState = new GuessingRoundState(this);
        currentState.start();
        EventBus.getInstance().post(
                GameEvent.Type.ROUND_STARTED,
                "Starting " + currentState.getStateName()
        );
    }

    @Override
    public void nextRound() {
        currentState.end();
        EventBus.getInstance().post(
                GameEvent.Type.ROUND_ENDED,
                currentState.getStateName() + " complete"
        );
        currentState = currentState.nextState();
        if (currentState == null) {
            gameOver = true;
            EventBus.getInstance().post(
                    GameEvent.Type.GAME_OVER,
                    "Game over! Winner: " + getWinner().getName()
            );
            return;
        }
        currentState.start();
        EventBus.getInstance().post(
                GameEvent.Type.ROUND_STARTED,
                "Starting " + currentState.getStateName()
        );
    }

    @Override
    public boolean isOver() {
        return gameOver;
    }

    @Override
    public IPlayer getWinner() {
        if (!isOver()) {
            return null;
        }
        return players.stream()
                .min((p1, p2) -> Integer.compare(p1.getScore(), p2.getScore()))
                .orElse(null);
    }

    @Override
    public List<IPlayer> getPlayers() {
        return List.copyOf(players);
    }

    @Override
    public IScoringStrategy getScoringStrategy() {
        return scoringStrategy;
    }

    @Override
    public void addPlayer(IPlayer player) {
        players.add(player);
        EventBus.getInstance().post(
                GameEvent.Type.PLAYER_ADDED,
                player.getName() + " joined the game"
        );
    }

    public IDeck getDeck() {
        return deck;
    }

    public IPlayer getHighestScorer() {
        return players.stream()
                .max((p1, p2) -> Integer.compare(p1.getScore(), p2.getScore()))
                .orElse(null);
    }

    public IGameState getCurrentState() {
        return currentState;
    }

    private void dealInitialHands() {
        for (IPlayer player : players) {
            player.clearHand();
            List<ridethebus.cards.ICard> cards = deck.dealHand(4);
            for (ridethebus.cards.ICard card : cards) {
                player.addCardToHand(card);
            }
        }
    }
}