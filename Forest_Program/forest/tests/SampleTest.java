package forest.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SampleTest {
    @Test
    public void testSuccess() {
        assertEquals(true, true);
    }

    @Test
    public void testFailed() {
        assertEquals(true, false);
    }
}