package ridethebus.game;

import ridethebus.characters.IPlayer;
import ridethebus.scoring.FamilyScoringStrategy;
import ridethebus.scoring.IScoringStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for creating a Game with a clean, readable setup.
 * Scoring strategy defaults to FamilyScoringStrategy if not specified.
 */
public class GameBuilder {

    private IScoringStrategy scoringStrategy = new FamilyScoringStrategy();
    private final List<IPlayer> players = new ArrayList<>();

    public GameBuilder withScoringStrategy(IScoringStrategy scoringStrategy) {
        this.scoringStrategy = scoringStrategy;
        return this;
    }

    public GameBuilder withPlayer(IPlayer player) {
        players.add(player);
        return this;
    }

    public GameBuilder withPlayers(List<IPlayer> players) {
        this.players.addAll(players);
        return this;
    }

    public Game build() {
        if (players.isEmpty()) {
            throw new IllegalStateException("Cannot build a game with no players");
        }
        Game game = new Game(scoringStrategy);
        for (IPlayer player : players) {
            game.addPlayer(player);
        }
        return game;
    }
}