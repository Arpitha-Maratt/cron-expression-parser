
package com.goavega.cron;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CronFieldParserTest {

    private final CronFieldParser parser = new CronFieldParser();

    @Test
    void shouldExpandWildcard() {
        List<Integer> result = parser.parseField("*", 0, 5);

        assertEquals(List.of(0, 1, 2, 3, 4, 5), result);
    }

    @Test
    void shouldExpandStepValues() {
        List<Integer> result = parser.parseField("*/15", 0, 59);

        assertEquals(List.of(0, 15, 30, 45), result);
    }

    @Test
    void shouldExpandCommaSeparatedValues() {
        List<Integer> result = parser.parseField("1,15", 1, 31);

        assertEquals(List.of(1, 15), result);
    }

    @Test
    void shouldExpandRange() {
        List<Integer> result = parser.parseField("1-5", 1, 7);

        assertEquals(List.of(1, 2, 3, 4, 5), result);
    }

    @Test
    void shouldRemoveDuplicateValues() {
        List<Integer> result = parser.parseField("1,3,1", 0, 5);

        assertEquals(List.of(1, 3), result);
    }

    @Test
    void shouldRejectInvalidValues() {
        assertThrows(
                IllegalArgumentException.class,
                () -> parser.parseField("70", 0, 59)
        );
    }

    @Test
    void shouldRejectInvalidRange() {
        assertThrows(
                IllegalArgumentException.class,
                () -> parser.parseField("5-2", 0, 59)
        );
    }
}
