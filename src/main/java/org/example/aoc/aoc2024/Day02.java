package org.example.aoc.aoc2024;

import java.util.Arrays;
import java.util.List;

class Day02 extends AoC2024Day<List<List<Integer>>> {

    @Override
    protected List<List<Integer>> parseInput(String strInput) {

        return strInput.lines()
                .map(l -> Arrays.stream(l.split("\\s+")).map(Integer::parseInt).toList())
                .toList();
    }

    @Override
    protected Integer partOne(List<List<Integer>> input) {

        int nSafe = 0;

        for (List<Integer> integers : input) {

            boolean isSafe = false;
            Boolean initialDirection = null;

            for (int i = 0; i < integers.size() - 1; i++) {

                final int a = integers.get(i);
                final int b = integers.get(i + 1);
                final boolean direction = b > a;

                if (initialDirection == null) {
                    initialDirection = direction;
                }

                isSafe = direction == initialDirection && Math.abs(a - b) > 0 && Math.abs(a - b) <= 3;

                if (!isSafe) {
                    break;
                }
            }

            if (isSafe) {
                nSafe++;
            }
        }

        return nSafe;
    }

    @Override
    protected Integer partTwo(List<List<Integer>> input) {

        int nSafe = 0;

        for (List<Integer> integers : input) {
            if (isSafeWithToleration(integers) || isSafeWithToleration(integers.reversed())) {
                nSafe++;
            }
        }

        return nSafe;
    }

    private boolean isSafeWithToleration(List<Integer> integers) {

        boolean isSafe = false;
        boolean alreadyTolerated = false;
        Boolean initialDirection = null;

        for (int i = 0; i < integers.size() - 1; i++) {

            final int a = integers.get(i);
            final int b = integers.get(i + 1);
            final boolean direction = b > a;

            if (initialDirection == null) {
                initialDirection = direction;
            }

            isSafe = isSafe(a, b, initialDirection);

            if (!isSafe) {
                if (alreadyTolerated) {
                    break;
                }

                if (i + 2 == integers.size()) {
                    isSafe = true;
                    break;
                }
                alreadyTolerated = true;

                isSafe = isSafe(a, integers.get(i + 2), initialDirection);

                if (!isSafe) {
                    break;
                }

                i = i + 1;
            }
        }

        return isSafe;
    }

    private boolean isSafe(int a, int b, boolean initialDirection) {

        final boolean direction = b > a;

        final int difference = Math.abs(a - b);
        return direction == initialDirection && difference > 0 && difference <= 3;
    }

    @Override
    protected String getDay() {

        return "day02";
    }
}
