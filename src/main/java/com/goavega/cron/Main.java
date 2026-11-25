
package com.goavega.cron;

public class Main {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println(
                    "Usage: java -jar cron-expression-parser.jar \"<cron expression>\""
            );
            return;
        }

        try {
            CronExpressionParser parser = new CronExpressionParser();
            parser.parse(args[0]);

        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
