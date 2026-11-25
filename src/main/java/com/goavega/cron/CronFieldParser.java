
package com.goavega.cron;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CronFieldParser {

    public List<Integer> parseField(String expression, int min, int max) {

        Set<Integer> values = new TreeSet<>();

        String[] parts = expression.split(",", -1);

        for (String part : parts) {

            String[] stepParts = part.split("/", -1);

            if (stepParts.length > 2) {
                throw new IllegalArgumentException("Invalid step expression: " + part);
            }

            int step = 1;

            if (stepParts.length == 2) {
                try {
                    step = Integer.parseInt(stepParts[1]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid step: " + part);
                }

                if (step <= 0) {
                    throw new IllegalArgumentException("Step must be positive");
                }
            }

            String range = stepParts[0];

            int start;
            int end;

            if (range.equals("*")) {

                start = min;
                end = max;

            } else if (range.contains("-")) {

                String[] rangeParts = range.split("-", -1);

                if (rangeParts.length != 2) {
                    throw new IllegalArgumentException("Invalid range: " + part);
                }

                try {
                    start = Integer.parseInt(rangeParts[0]);
                    end = Integer.parseInt(rangeParts[1]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid range: " + part);
                }

                if (start > end) {
                    throw new IllegalArgumentException("Range start cannot exceed end");
                }

            } else {

                if (stepParts.length == 2) {
                    throw new IllegalArgumentException("Step requires a wildcard or range");
                }

                try {
                    start = Integer.parseInt(range);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid value: " + part);
                }

                end = start;
            }

            if (start < min || end > max) {
                throw new IllegalArgumentException("Value outside allowed range");
            }

            for (int i = start; i <= end; i += step) {
                values.add(i);
            }
        }

        return new ArrayList<>(values);
    }
}
