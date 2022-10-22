package org.example.aoc2016;

import java.util.Arrays;
import java.util.List;

class Day02 extends AbstractAoC2016<String, List<List<Day02.Direction>>> {

    protected enum Direction {
        U,
        D,
        L,
        R
    }

    @Override
    protected List<List<Direction>> parseInput(String strInput) {

        return Arrays.stream(strInput.split("\n"))
                .map(line -> Arrays.stream(line.trim().split("")).map(Direction::valueOf).toList())
                .toList();
    }

    @Override
    protected String partOne(List<List<Direction>> input) {

        int i = 5;
        StringBuilder result = new StringBuilder();

        for (List<Direction> directions : input) {

            for (Direction direction : directions) {
                if (!outOfBounds(i, direction)) {
                    switch (direction) {
                        case U -> i -= 3;
                        case D -> i += 3;
                        case L -> i -= 1;
                        case R -> i += 1;
                    }
                }
            }
            result.append(i);
        }

        return result.toString();
    }

    private boolean outOfBounds(int n, Direction direction) {

        if ((n == 1 || n == 2 || n == 3) && direction == Direction.U) {
            return true;
        } else if ((n == 7 || n == 8 || n == 9) && direction == Direction.D) {
            return true;
        } else if ((n == 1 || n == 4 || n == 7) && direction == Direction.L) {
            return true;
        } else return (n == 3 || n == 6 || n == 9) && direction == Direction.R;
    }

    @Override
    protected String partTwo(List<List<Direction>> input) {
        return null;
    }

    @Override
    protected String getDay() {

        return "day02";
    }
}
