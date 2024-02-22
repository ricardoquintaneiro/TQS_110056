/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.sets.BoundedSetOfNaturals;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;

    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[] { 10, 20, 30, 40, 50, 60 });
        setC = BoundedSetOfNaturals.fromArray(new int[] { 50, 60 });
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    @Test
    public void testAddElement() {
        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            setB.add(11);
        });
        assertEquals(exception.getMessage(), "bounded set is full. no more elements allowed.");

        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            setB.add(50);
        });
        assertEquals(exception.getMessage(), "duplicate value: 50");
    }

    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[] { 10, -20, -30 };
        BoundedSetOfNaturals newSet = new BoundedSetOfNaturals(elems.length);

        // must fail with exception
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            newSet.add(elems);
        });
        assertEquals("Illegal argument: not a natural number", exception.getMessage());
    }

}
