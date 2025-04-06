package inf112.firegirlwaterboy.model.managers;

import inf112.firegirlwaterboy.model.entity.Player;

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
    public int getTotalCollectedScore();
}
