package org.example.aoc.aoc2024;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day03 extends AoC2024Day<String> {

    private final Pattern pattern = Pattern.compile("mul\\((\\d\\d?\\d?),(\\d\\d?\\d?)\\)");

    private final Pattern conditionalStatementsPattern = Pattern.compile("do\\(\\)|don't\\(\\)|mul\\((\\d\\d?\\d?),(\\d\\d?\\d?)\\)");

    @Override
    protected String parseInput(String strInput) {

        return strInput;
    }

    @Override
    protected Integer partOne(String input) {

        final Matcher matcher = pattern.matcher(input);

        int r = 0;

        while (matcher.find()) {
            final int a = Integer.parseInt(matcher.group(1));
            final int b = Integer.parseInt(matcher.group(2));
            r += a * b;
        }

        return r;
    }

    @Override
    protected Integer partTwo(String input) {

        final Matcher matcher = conditionalStatementsPattern.matcher(input);

        boolean enabled = true;
        int r = 0;

        while (matcher.find()) {

            if (matcher.group().equals("do()")) {
                enabled = true;
            } else if (matcher.group().equals("don't()")) {
                enabled = false;
            } else if (enabled) {

                final int a = Integer.parseInt(matcher.group(1));
                final int b = Integer.parseInt(matcher.group(2));
                r += a * b;
            }
        }

        return r;
    }

    @Override
    protected String getDay() {

        return "day03";
    }
}
