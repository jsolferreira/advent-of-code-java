package org.example.aoc.aoc2020;

class Day03 extends AoC2020Day<char[][]> {

    @Override
    protected char[][] parseInput(String strInput) {

        return strInput.lines()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    @Override
    protected Long partOne(char[][] input) {

        return calculateNumberOfTrees(input, 3, 1);
    }

    @Override
    protected Long partTwo(char[][] input) {

        return calculateNumberOfTrees(input, 1, 1) *
                calculateNumberOfTrees(input, 3, 1) *
                calculateNumberOfTrees(input, 5, 1) *
                calculateNumberOfTrees(input, 7, 1) *
                calculateNumberOfTrees(input, 1, 2);
    }

    private long calculateNumberOfTrees(char[][] input, int right, int down) {

        int nTrees = 0;
        int i = 0;
        int j = 0;

        while (i < input.length - 1) {

            i += down;

            final char[] row = input[i];

            j = (j + right) % row.length;

            if (row[j] == '#') {

                nTrees++;
            }
        }

        return nTrees;
    }

    @Override
    protected String getDay() {

        return "day03";
    }
}
