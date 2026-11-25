
package com.goavega.cron;

import java.util.List;

public class CronExpressionParser {

    private final CronFieldParser fieldParser = new CronFieldParser();

    public void parse(String expression) {

        String[] parts = expression.trim().split("\\s+", 6);

        if (parts.length != 6) {
            throw new IllegalArgumentException(
                    "Cron expression must contain 5 time fields and a command"
            );
        }

        String[] fieldNames = {
                "minute",
                "hour",
                "day of month",
                "month",
                "day of week"
        };

        int[] minValues = {0, 0, 1, 1, 0};
        int[] maxValues = {59, 23, 31, 12, 7};

        for (int i = 0; i < 5; i++) {

            List<Integer> values = fieldParser.parseField(
                    parts[i],
                    minValues[i],
                    maxValues[i]
            );

            System.out.printf("%-14s", fieldNames[i]);

            System.out.print(
                    values.stream()
                            .map(String::valueOf)
                            .collect(java.util.stream.Collectors.joining(" "))
            );
            System.out.println();
        }

        System.out.printf("%-14s%s%n", "command", parts[5]);
    }
}
