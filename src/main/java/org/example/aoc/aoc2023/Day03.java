package org.example.aoc.aoc2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Day03 extends AoC2023Day<char[][]> {

    private record Position(int i, int j) {}

    @Override
    protected char[][] parseInput(String strInput) {

        return strInput.lines()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    @Override
    protected Integer partOne(char[][] input) {

        int sum = 0;

        for (int i = 0; i < input.length; i++) {

            final char[] row = input[i];
            boolean isAdjacent = false;
            StringBuilder numberDigits = new StringBuilder();

            for (int j = 0; j < row.length; j++) {
                final char cell = input[i][j];

                if (Character.isDigit(cell)) {

                    numberDigits.append(cell);

                    if (!isAdjacent) {

                        isAdjacent = checkAdjacency(input, i, j);
                    }
                } else {

                    if (isAdjacent) {
                        sum += Integer.parseInt(numberDigits.toString());
                    }

                    isAdjacent = false;
                    numberDigits = new StringBuilder();
                }
            }

            if (isAdjacent) {
                sum += Integer.parseInt(numberDigits.toString());
            }
        }

        return sum;
    }

    private boolean checkAdjacency(char[][] input, int i, int j) {

        return Stream.of(
                        getPosition(input, i - 1, j - 1),
                        getPosition(input, i - 1, j),
                        getPosition(input, i - 1, j + 1),
                        getPosition(input, i, j - 1),
                        getPosition(input, i, j + 1),
                        getPosition(input, i + 1, j - 1),
                        getPosition(input, i + 1, j),
                        getPosition(input, i + 1, j + 1)
                )
                .filter(Objects::nonNull)
                .anyMatch(cell -> !Character.isDigit(cell) && cell != '.');
    }

    private Character getPosition(char[][] input, int i, int j) {

        if (i < 0 || i >= input.length || j < 0 || j >= input[i].length) {

            return null;
        }

        return input[i][j];
    }

    @Override
    protected Integer partTwo(char[][] input) {

        final Map<Position, List<Integer>> gears = new HashMap<>();

        for (int i = 0; i < input.length; i++) {

            final char[] row = input[i];
            StringBuilder numberDigits = new StringBuilder();
            Set<Position> positions = new HashSet<>();

            for (int j = 0; j < row.length; j++) {
                final char cell = input[i][j];

                if (Character.isDigit(cell)) {

                    numberDigits.append(cell);
                    positions.addAll(getAdjacentGears(input, i, j));
                } else {

                    if (!numberDigits.isEmpty()) {
                        for (Position position : positions) {
                            List<Integer> integers = gears.getOrDefault(position, new ArrayList<>());

                            integers.add(Integer.parseInt(numberDigits.toString()));
                            gears.put(position, integers);
                        }
                    }

                    numberDigits = new StringBuilder();
                    positions = new HashSet<>();
                }
            }

            if (!numberDigits.isEmpty()) {
                for (Position position : positions) {
                    List<Integer> integers = gears.getOrDefault(position, new ArrayList<>());

                    integers.add(Integer.parseInt(numberDigits.toString()));
                    gears.put(position, integers);
                }
            }
        }

        return gears.values().stream()
                .filter(partNumbers -> partNumbers.size() == 2)
                .map(partNumbers -> partNumbers.stream().reduce(1, (acc, val) -> acc * val))
                .reduce(0, Integer::sum);
    }

    private List<Position> getAdjacentGears(char[][] input, int i, int j) {

        return IntStream.rangeClosed(i - 1, i + 1)
                .boxed()
                .flatMap(ii -> IntStream.rangeClosed(j - 1, j + 1)
                        .filter(jj -> ii != i || jj != j)
                        .filter(jj -> {
                            Character position = getPosition(input, ii, jj);

                            return position != null && position == '*';
                        })
                        .mapToObj(jj -> new Position(ii, jj)))
                .toList();
    }

    @Override
    protected String getDay() {

        return "day03";
    }
}
