package inf112.firegirlwaterboy.model.entity.managers;

import inf112.firegirlwaterboy.model.entity.Player;
import inf112.firegirlwaterboy.model.entity.types.PlayerType;

/**
 * Interface for a set of players.
 * This interface defines the methods that can be used to interact with a set of
 * players.
 */
public interface IPlayerSet extends IEntitySet<Player> {

    /**
     * Returns the total score collected by all players.
     *
     * @return
     */
    public int getScore();

    /**
     * @return true if all players are finished, false otherwise
     */
    boolean areFinished();

    /**
     * @param playerType the type of player to check
     * @return true playerSer contains playerType, false otherwise
     */
    boolean contains(PlayerType playerType);

    /**
     * @param playerType the type of player to get
     * @return the player of the given type
     * @throws IllegalArgumentException if the playerSet does not contain a player
     *                                  of the given type
     */
    Player getPlayer(PlayerType playerType);

    /**
     * @return true if all players are alive, false otherwise
     */
    boolean areAlive();
}
