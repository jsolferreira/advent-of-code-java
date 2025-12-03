package org.example.aoc.aoc2025;

import java.util.List;

class Day03 extends AoC2025Day<List<String>> {

    @Override
    protected List<String> parseInput(String strInput) {

        return strInput.lines().toList();
    }

    @Override
    protected Long partOne(List<String> input) {

        long sum = 0;

        for (String string : input) {

            char c1 = string.charAt(0);
            char c2 = string.charAt(1);

            for (int i = 2; i < string.length(); i++) {

                char c = string.charAt(i);

                if (c2 > c1) {
                    c1 = c2;
                    c2 = c;
                }

                if (c > c2) {
                    c2 = c;
                }
            }

            sum += Long.parseLong("" + c1 + c2);
        }

        return sum;
    }

    @Override
    protected Integer partTwo(List<String> input) {

        return null;
    }

    @Override
    protected String getDay() {

        return "day03";
    }
}
