package org.example.aoc.aoc2020;

import java.util.List;

class Day05 extends AoC2020Day<List<String>> {

    private record Range(int lowerBound, int upperBound) {
    }

    @Override
    protected List<String> parseInput(String strInput) {

        return strInput.lines().toList();
    }

    @Override
    protected Long partOne(List<String> input) {

        return input.stream()
                .mapToLong(seat -> {

                    final int row = findRow(seat);
                    final int column = findColumn(seat);

                    return row * 8L + column;
                })
                .max()
                .orElseThrow();
    }

    @Override
    protected Long partTwo(List<String> input) {

        final List<Integer> seats = input.stream()
                .map(seat -> {

                    final int row = findRow(seat);
                    final int column = findColumn(seat);

                    return row * 8 + column;
                })
                .sorted()
                .toList();

        for (int i = 0; i < seats.size() - 2; i++) {

            if (seats.get(i) != seats.get(i + 1) - 1) {

                return seats.get(i + 1) - 1L;
            }
        }

        return null;
    }

    private int findRow(String seat) {

        return seat.substring(0, 7)
                .chars()
                .mapToObj(c -> (char) c)
                .reduce(new Range(0, 127),
                        (acc, val) -> val == 'F' ? lowerHalf(acc) : upperHalf(acc),
                        (a, b) -> new Range(-1, -1))
                .lowerBound;
    }

    private int findColumn(String seat) {

        return seat.substring(7)
                .chars()
                .mapToObj(c -> (char) c)
                .reduce(new Range(0, 7),
                        (acc, val) -> val == 'L' ? lowerHalf(acc) : upperHalf(acc),
                        (a, b) -> new Range(-1, -1))
                .lowerBound;
    }

    private Range lowerHalf(Range range) {

        return new Range(range.lowerBound, (range.lowerBound + range.upperBound) / 2);
    }

    private Range upperHalf(Range range) {

        return new Range((int) Math.ceil((float) (range.lowerBound + range.upperBound) / 2), range.upperBound);
    }

    @Override
    protected String getDay() {

        return "day05";
    }
}
