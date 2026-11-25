
package com.goavega.cron;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CronExpressionParserTest {

    private final CronExpressionParser parser = new CronExpressionParser();

    @Test
    void shouldParseValidCronExpression() {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            parser.parse("*/15 0 1,15 * 1-5 /usr/bin/find");

        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();

        String expected =
                "minute        0 15 30 45" + System.lineSeparator() +
                        "hour          0" + System.lineSeparator() +
                        "day of month  1 15" + System.lineSeparator() +
                        "month         1 2 3 4 5 6 7 8 9 10 11 12" + System.lineSeparator() +
                        "day of week   1 2 3 4 5" + System.lineSeparator() +
                        "command       /usr/bin/find" + System.lineSeparator();

        assertEquals(expected, result);    }

    @Test
    void shouldRejectInvalidNumberOfFields() {

        assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse("*/15 0 1,15 *")
        );
    }

    @Test
    void shouldRejectInvalidMinute() {

        assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse("70 0 1,15 * 1-5 /usr/bin/find")
        );
    }
}
