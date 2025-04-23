package inf112.firegirlwaterboy.model;

import static org.mockito.Mockito.*;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

import inf112.firegirlwaterboy.model.entity.Collectable;
import inf112.firegirlwaterboy.model.entity.Element;
import inf112.firegirlwaterboy.model.entity.Player;
import inf112.firegirlwaterboy.model.maps.LayerType;
import inf112.firegirlwaterboy.model.entity.Platform;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyContactListenerTest {

    private GameContactListener contactListener;
    private Contact mockContact;
    private Fixture mockFixtureA;
    private Fixture mockFixtureB;
    private Player mockPlayer;
    private Platform mockPlatform;
    private Collectable mockCollectable;
    private Element mockElement;

    @BeforeEach
    void setUp() {
        contactListener = new GameContactListener();
        mockContact = mock(Contact.class);
        mockFixtureA = mock(Fixture.class);
        mockFixtureB = mock(Fixture.class);
        mockPlayer = mock(Player.class);
        mockPlatform = mock(Platform.class);
        mockCollectable = mock(Collectable.class);
        mockElement = mock(Element.class);

        when(mockContact.getFixtureA()).thenReturn(mockFixtureA);
        when(mockContact.getFixtureB()).thenReturn(mockFixtureB);
    }

    @Test
    void beginContact_playerAndFinish_setsFinishedTrue() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlayer);
        when(mockFixtureB.getUserData()).thenReturn(LayerType.FINISH);

        contactListener.beginContact(mockContact);

        verify(mockPlayer).setFinished(true);
    }

    @Test
    void beginContact_playerAndCollectable_interactsWithCollectable() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlayer);
        when(mockFixtureB.getUserData()).thenReturn(mockCollectable);

        contactListener.beginContact(mockContact);

        verify(mockPlayer).interactWithCollectable(mockCollectable);
    }

    @Test
    void beginContact_playerAndElement_interactsWithElement() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlayer);
        when(mockFixtureB.getUserData()).thenReturn(mockElement);

        contactListener.beginContact(mockContact);

        verify(mockPlayer).interactWithElement(mockElement);
    }

    @Test
    void beginContact_platformAndLayerTypeStatic_callsPlatformCollision() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlatform);
        when(mockFixtureB.getUserData()).thenReturn(LayerType.STATIC);

        contactListener.beginContact(mockContact);

        verify(mockPlatform).collision();
    }

    @Test
    void endContact_playerAndFinish_setsFinishedFalse() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlayer);
        when(mockFixtureB.getUserData()).thenReturn(LayerType.FINISH);

        contactListener.endContact(mockContact);

        verify(mockPlayer).setFinished(false);
    }

    @Test
    void endContact_platformAndLayerTypeStatic_doesNotCallPlatformCollision() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlatform);
        when(mockFixtureB.getUserData()).thenReturn(LayerType.STATIC);

        contactListener.endContact(mockContact);

        verify(mockPlatform, never()).collision();
    }

    @Test
    void platformCollision_withLayerTypeStatic_callsCollision() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlatform);
        when(mockFixtureB.getUserData()).thenReturn(LayerType.STATIC);

        contactListener.beginContact(mockContact); // Trigger platformCollision through beginContact

        verify(mockPlatform).collision();
    }

    @Test
    void platformCollision_withoutLayerTypeStaticOrLayerTypeStatic_doesNotCallCollision() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlatform);
        for (LayerType layerType : LayerType.values()) {
            if (layerType != LayerType.STATIC) {
                when(mockFixtureB.getUserData()).thenReturn(layerType);
                contactListener.beginContact(mockContact); // Trigger platformCollision through beginContact
                verify(mockPlatform, never()).collision();
            }
        }
    }
}
