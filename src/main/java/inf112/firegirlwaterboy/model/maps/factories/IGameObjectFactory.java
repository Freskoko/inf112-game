package inf112.firegirlwaterboy.model.maps.factories;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.model.entity.Collectable;
import inf112.firegirlwaterboy.model.entity.Element;
import inf112.firegirlwaterboy.model.entity.Platform;

public interface IGameObjectFactory {

    /**
     * Creates a new plaform based on {@code object} in the {@code world}
     *
     * @param world  the game world
     * @param object the representation of the platform
     * @return a plaform object
     */
    Platform createPlatform(World world, MapObject object);

    /**
     * Creates a new element based on {@code object} in the {@code world}
     *
     * @param world  the game world
     * @param object the representation of the element
     * @return an element object
     */
    Element createElement(World world, MapObject object);

    /**
     * Creates a new collectable based on {@code object} in the {@code world}
     *
     * @param world  the game world
     * @param object the representation of the collectable
     * @return a collectable object
     */
    Collectable createCollectable(World world, MapObject object);

}
