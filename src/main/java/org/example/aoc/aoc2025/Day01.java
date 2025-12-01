package org.example.aoc.aoc2025;

import org.example.aoc.utils.Pair;

import java.util.List;

class Day01 extends AoC2025Day<List<Pair<Day01.Direction, Integer>>> {

    protected enum Direction {
        L,
        R
    }

    @Override
    protected List<Pair<Direction, Integer>> parseInput(String strInput) {

        return strInput.lines()
                .map(s -> new Pair<>(Direction.valueOf(s.substring(0, 1)), Integer.parseInt(s.substring(1))))
                .toList();
    }

    @Override
    protected Integer partOne(List<Pair<Direction, Integer>> input) {

        int value = 50;
        int nZeroes = 0;

        for (Pair<Direction, Integer> rotation : input) {

            final Direction direction = rotation.left();
            final int distance = rotation.right();

            final int tempValue = direction == Direction.L
                    ? value - distance
                    : value + distance;

            value = (100 + tempValue) % 100;

            if (value == 0) {
                nZeroes++;
            }
        }

        return nZeroes;
    }

    @Override
    protected Integer partTwo(List<Pair<Direction, Integer>> input) {

        int value = 50;
        int nZeroes = 0;

        for (Pair<Direction, Integer> rotation : input) {

            final Direction direction = rotation.left();
            final int distance = rotation.right();
            final int modDistance = distance % 100;

            nZeroes += distance / 100;

            final int tempValue = direction == Direction.L
                    ? value - modDistance
                    : value + modDistance;

            if (value == 0 || tempValue < 0 || tempValue > 100) {
                nZeroes++;
            }

            value = (100 + tempValue) % 100;
        }

        return nZeroes;
    }

    @Override
    protected String getDay() {

        return "day01";
    }
}
