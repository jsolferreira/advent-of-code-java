package org.example.aoc.aoc2019;

class Day04 extends AoC2019Day<Day04.Range> {

    protected record Range(int start, int end) {}

    @Override
    protected Range parseInput(String strInput) {

        final String[] split = strInput.split("-");

        return new Range(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    @Override
    protected Long partOne(Range input) {

        long count = 0;

        for (int i = input.start; i < input.end; i++) {

            final String asString = String.valueOf(i);

            boolean increasing = true;
            boolean isAdjacent = false;

            for (int j = 1; j < asString.length(); j++) {

                final char c1 = asString.charAt(j - 1);
                final char c2 = asString.charAt(j);

                if (c2 < c1) {
                    increasing = false;
                    break;
                }

                if (c1 == c2) {
                    isAdjacent = true;
                }
            }

            if (increasing && isAdjacent) {
                count++;
            }
        }

        return count;
    }

    @Override
    protected Long partTwo(Range input) {

        long count = 0;

        for (int i = input.start; i < input.end; i++) {

            final String asString = String.valueOf(i);

            boolean increasing = true;
            int nAdjacent = 0;
            boolean isAdjacent = false;

            for (int j = 1; j < asString.length(); j++) {

                final char c1 = asString.charAt(j - 1);
                final char c2 = asString.charAt(j);

                if (c2 < c1) {
                    increasing = false;
                    break;
                }

                if (c1 == c2) {
                    nAdjacent++;
                } else {
                    if (!isAdjacent && nAdjacent == 1) {
                        isAdjacent = true;
                    }

                    nAdjacent = 0;
                }
            }

            if (!isAdjacent && nAdjacent == 1) {
                isAdjacent = true;
            }

            if (increasing && isAdjacent) {
                count++;
            }
        }

        return count;
    }

    @Override
    protected String getDay() {

        return "day04";
    }
}
