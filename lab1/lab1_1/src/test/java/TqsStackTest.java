import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import stack.TqsStack;

public class TqsStackTest {
    TqsStack<String> wordsStack;

    @BeforeEach
    void setUp() {
        wordsStack = new TqsStack<>();
    }

    @DisplayName("A stack is empty on construction")
    @Test
    void isEmpty() {
        // arrange (required objects, initial state, ...)
        // BeforeEach already creates empty stack so nothing to do here

        // act
        // nothing to do here

        // assess
        assertTrue(wordsStack.isEmpty());
    }

    @DisplayName("A stack has size 0 on construction")
    @Test
    void hasSize0OnConstruction() {
        assertTrue(wordsStack.size() == 0);
    }

    @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n")
    @Test
    void checkSizeAfterPushesToEmptyStack() {
        int n = 10;

        for (int i = 0; i < n; i++) {
            wordsStack.push(String.valueOf(i));
        }

        assertTrue(!wordsStack.isEmpty());
        assertTrue(wordsStack.size() == n);
    }

    @DisplayName("If one pushes x then pops, the value popped is x")
    @Test
    void checkPopLifo() {
        wordsStack.push("Alpha");
        wordsStack.push("Bravo");

        Assertions.assertAll(
                () -> assertEquals("Bravo", wordsStack.pop()),
                () -> assertEquals("Alpha", wordsStack.pop()),
                () -> assertTrue(wordsStack.isEmpty()));
    }

    @DisplayName("If one pushes x then peeks, the value is x, but the size stays the same")
    @Test
    void checkPeekAndSizeAfter() {
        wordsStack.push("Alpha");

        Assertions.assertAll(
                () -> assertEquals("Alpha", wordsStack.peek()),
                () -> assertEquals(1, wordsStack.size()));
    }

    @DisplayName("If the size is n, then after n pops, the stack is empty and has size 0")
    @Test
    void checkSize0AfterNsizePops() {
        wordsStack.push("Alpha");
        wordsStack.push("Bravo");

        int n = wordsStack.size();

        for (int i = 0; i < n; i++) {
            wordsStack.pop();
        }

        Assertions.assertAll(
                () -> assertTrue(wordsStack.isEmpty()),
                () -> assertEquals(0, wordsStack.size()));

    }

    @DisplayName("Popping from an empty stack does throw a NoSuchElementException")
    @Test
    void checkPoppingWhenEmptyException() {
        NoSuchElementException thrown = Assertions.assertThrows(NoSuchElementException.class, () -> {
            wordsStack.pop();
        }, "NoSuchElement exception");

        
    }
}
