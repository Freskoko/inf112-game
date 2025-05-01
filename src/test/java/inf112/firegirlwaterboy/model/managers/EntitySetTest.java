package inf112.firegirlwaterboy.model.managers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;

import inf112.firegirlwaterboy.model.entity.Element;
import inf112.firegirlwaterboy.model.entity.IEntity;
import inf112.firegirlwaterboy.model.entity.managers.EntitySet;
import inf112.firegirlwaterboy.model.entity.types.ElementType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntitySetTest {

    private EntitySet<Element> entitySet;
    private Batch mockBatch;
    private Element mockEntity1;
    private Element mockEntity2;

    @BeforeEach
    void setUp() {
        entitySet = new EntitySet<>();
        mockBatch = mock(Batch.class);
        mockEntity1 = mock(Element.class);
        mockEntity2 = mock(Element.class);

        when(mockEntity1.getType()).thenReturn(ElementType.LAVA);
        when(mockEntity2.getType()).thenReturn(ElementType.WATER);
    }

    @Test
    void testAddEntity() {
        assertTrue(entitySet.isEmpty());
        entitySet.add(mockEntity1);
        assertFalse(entitySet.isEmpty());
        assertTrue(entitySet.contains(mockEntity1));
    }

    @Test
    void testAddExistingEntity() {
        entitySet.add(mockEntity1);
        assertThrows(IllegalArgumentException.class, () -> entitySet.add(mockEntity1));
    }

    @Test
    void testDraw() {
        entitySet.add(mockEntity1);
        entitySet.add(mockEntity2);
        entitySet.draw(mockBatch);
        verify(mockEntity1).draw(mockBatch);
        verify(mockEntity2).draw(mockBatch);
    }

    @Test
    void testDispose() {
        entitySet.add(mockEntity1);
        entitySet.add(mockEntity2);
        entitySet.dispose();
        verify(mockEntity1).dispose();
        verify(mockEntity2).dispose();
    }

    @Test
    void testIterator() {
        entitySet.add(mockEntity1);
        entitySet.add(mockEntity2);
        List<IEntity<ElementType>> entities = new ArrayList<>();
        for (IEntity<ElementType> entity : entitySet) {
            entities.add(entity);
        }
        assertEquals(2, entities.size());
        assertTrue(entities.contains(mockEntity1));
        assertTrue(entities.contains(mockEntity2));
    }

    @Test
    void testIsEmpty() {
        assertTrue(entitySet.isEmpty());
        entitySet.add(mockEntity1);
        assertFalse(entitySet.isEmpty());
    }

    @Test
    void testToStringEmptySet() {
        assertEquals("", entitySet.toString());
    }

    @Test
    void testContains() {
        entitySet.add(mockEntity1);
        assertTrue(entitySet.contains(mockEntity1));
        assertFalse(entitySet.contains(mockEntity2));
    }

    @Test
    void testUpdate() {
        entitySet.add(mockEntity1);
        entitySet.add(mockEntity2);
        entitySet.update();
        verify(mockEntity1).update();
        verify(mockEntity2).update();
    }
}