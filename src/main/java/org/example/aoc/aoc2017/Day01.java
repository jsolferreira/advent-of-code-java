package org.example.aoc.aoc2017;

class Day01 extends AoC2017Day<String> {

    @Override
    protected String parseInput(String strInput) {

        return strInput;
    }

    @Override
    protected Long partOne(String input) {

        return run(input, 1);
    }

    @Override
    protected Long partTwo(String input) {

        return run(input, input.length() / 2);
    }

    @Override
    protected String getDay() {

        return "day01";
    }

    private long run(String input, int nSteps) {

        long n = 0;

        for (int i = 0; i < input.length(); i++) {

            char c1 = input.charAt(i);
            char c2 = input.charAt((i + nSteps) % input.length());

            if (c1 == c2) {
                n += Character.getNumericValue(c1);
            }
        }

        return n;
    }
}
