# Ride-The-Bus-Family-Edition


Our digital version of Ride the Bus will include all the structure of the original game with players competing through multiple rounds of guessing cards; the game can be played with one solo player, or be set up for all players to compete for the lowest score.


Patterns: 


Strategy Pattern: IScoringStrategy swaps scoring rules between Family and Adult game modes, allowing the game to change scoring behavior at runtime without altering core game logic.

Factory Pattern: DeckFactory handles the creation of Deck objects, whether a standard deck or a modified variant. This allows the rest of the game to depend only on the Deck type while construction is contained entirely within the factory. New deck variants can be added without touching any other game logic.

Factory Pattern: PlayerFactory handles creation of both human and AI player types. Rather than the main game class managing how players are constructed, the factory owns that responsibility and makes it easy to add new player types later.

Observer Pattern: IGameObserver and IGameSubject will keep UI components updated as the game state changes. Score displays, card visuals, and phase indicators can all react to game events without being tightly coupled to game logic.

State Pattern: IGameState will manage transitions between game phases, including the Guessing Round, Pyramid Round, and Bus Round. Each state encapsulates its own rules and actions, so the game progresses cleanly from phase to phase without the main game class managing that logic directly.
