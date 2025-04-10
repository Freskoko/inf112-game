package inf112.firegirlwaterboy.model;

import static org.mockito.Mockito.*;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

import inf112.firegirlwaterboy.model.entity.Collectable;
import inf112.firegirlwaterboy.model.entity.Element;
import inf112.firegirlwaterboy.model.entity.Player;
import inf112.firegirlwaterboy.model.entity.Platform;
import inf112.firegirlwaterboy.model.types.ElementType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyContactListenerTest {

    private MyContactListener contactListener;
    private Contact mockContact;
    private Fixture mockFixtureA;
    private Fixture mockFixtureB;
    private Player mockPlayer;
    private Platform mockPlatform;
    private Collectable mockCollectable;
    private Element mockElement;

    @BeforeEach
    void setUp() {
        contactListener = new MyContactListener();
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
    void beginContact_playerAndHorizontal_setsOnGroundTrue() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlayer);
        when(mockFixtureB.getUserData()).thenReturn("Horizontal");

        contactListener.beginContact(mockContact);

        verify(mockPlayer).setOnGround(true);
    }

    @Test
    void beginContact_playerAndEdge_setsTouchingEdgeTrue() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlayer);
        when(mockFixtureB.getUserData()).thenReturn("Edges");

        contactListener.beginContact(mockContact);

        verify(mockPlayer).setTouchingEdge(true);
    }

    @Test
    void beginContact_playerAndFinish_setsFinishedTrue() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlayer);
        when(mockFixtureB.getUserData()).thenReturn("Finish");

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
        when(mockElement.getType()).thenReturn(ElementType.LAVA);

        contactListener.beginContact(mockContact);

        verify(mockPlayer).interactWithElement(ElementType.LAVA);
    }

    @Test
    void beginContact_playerAndPlatform_setsOnPlatform() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlayer);
        when(mockFixtureB.getUserData()).thenReturn(mockPlatform);

        contactListener.beginContact(mockContact);

        verify(mockPlayer).setOnPlatform(mockPlatform);
    }

    @Test
    void beginContact_platformAndHorizontal_callsPlatformCollision() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlatform);
        when(mockFixtureB.getUserData()).thenReturn("Horizontal");

        contactListener.beginContact(mockContact);

        verify(mockPlatform).collision();
    }

    @Test
    void endContact_playerAndHorizontal_setsOnGroundFalse() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlayer);
        when(mockFixtureB.getUserData()).thenReturn("Horizontal");

        contactListener.endContact(mockContact);

        verify(mockPlayer).setOnGround(false);
    }

    @Test
    void endContact_playerAndEdge_setsTouchingEdgeFalse() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlayer);
        when(mockFixtureB.getUserData()).thenReturn("Edges");

        contactListener.endContact(mockContact);

        verify(mockPlayer).setTouchingEdge(false);
    }

    @Test
    void endContact_playerAndFinish_setsFinishedFalse() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlayer);
        when(mockFixtureB.getUserData()).thenReturn("Finish");

        contactListener.endContact(mockContact);

        verify(mockPlayer).setFinished(false);
    }

    @Test
    void endContact_platformAndEdge_doesNotCallPlatformCollision() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlatform);
        when(mockFixtureB.getUserData()).thenReturn("Edges");

        contactListener.endContact(mockContact);

        verify(mockPlatform, never()).collision();
    }

    @Test
    void endContact_platformAndHorizontal_doesNotCallPlatformCollision() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlatform);
        when(mockFixtureB.getUserData()).thenReturn("Horizontal");

        contactListener.endContact(mockContact);

        verify(mockPlatform, never()).collision();
    }

    @Test
    void platformCollision_withHorizontal_callsCollision() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlatform);
        when(mockFixtureB.getUserData()).thenReturn("Horizontal");

        contactListener.beginContact(mockContact); // Trigger platformCollision through beginContact

        verify(mockPlatform).collision();
    }

    @Test
    void platformCollision_withoutEdgeOrHorizontal_doesNotCallCollision() {
        when(mockFixtureA.getUserData()).thenReturn(mockPlatform);
        when(mockFixtureB.getUserData()).thenReturn("SomeOtherObject");

        contactListener.beginContact(mockContact); // Trigger platformCollision through beginContact

        verify(mockPlatform, never()).collision();
    }
}
