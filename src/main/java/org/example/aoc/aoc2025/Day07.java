package org.example.aoc.aoc2025;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Day07 extends AoC2025Day<char[][]> {

    private static final char START = 'S';

    private static final char SPLITTER = '^';

    private record Position(int i, int j) {}

    @Override
    protected char[][] parseInput(String strInput) {

        return strInput.lines()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    @Override
    protected Long partOne(char[][] input) {

        Set<Position> positions = new HashSet<>();
        boolean foundStart = false;

        for (int i = 0; i < input.length && !foundStart; i++) {
            for (int j = 0; j < input[i].length && !foundStart; j++) {
                if (input[i][j] == START) {
                    positions = gatherPositions(input, new HashSet<>(), i + 1, j);
                    foundStart = true;
                }
            }
        }

        return (long) positions.size();
    }

    private Set<Position> gatherPositions(char[][] input, Set<Position> visited, int i, int j) {

        if (i < 0 || i >= input.length || j < 0 || j >= input[0].length) {
            return new HashSet<>();
        }

        if (input[i][j] == SPLITTER) {
            final Set<Position> p1 = visited.contains(new Position(i, j - 1)) ? new HashSet<>() : gatherPositions(input, visited, i, j - 1);
            final Set<Position> p2 = visited.contains(new Position(i, j + 1)) ? new HashSet<>() : gatherPositions(input, visited, i, j + 1);

            final Set<Position> positions = new HashSet<>();
            positions.add(new Position(i, j));
            positions.addAll(p1);
            positions.addAll(p2);

            return positions;
        } else {

            visited.add(new Position(i, j));

            return gatherPositions(input, visited, i + 1, j);
        }
    }

    @Override
    protected Long partTwo(char[][] input) {

        long total = 0;
        boolean foundStart = false;

        for (int i = 0; i < input.length && !foundStart; i++) {
            for (int j = 0; j < input[i].length && !foundStart; j++) {
                if (input[i][j] == START) {
                    total = countDifferenceTimelines(input, new HashMap<>(), i + 1, j);
                    foundStart = true;
                }
            }
        }

        return total;
    }

    private Long countDifferenceTimelines(char[][] input, Map<Position, Long> visited, int i, int j) {

        if (i < 0 || i >= input.length || j < 0 || j >= input[0].length) {
            return 1L;
        }

        if (input[i][j] == SPLITTER) {
            final Position left = new Position(i, j - 1);
            final Position right = new Position(i, j + 1);

            final long p1 = visited.containsKey(left) ? visited.get(left) : countDifferenceTimelines(input, visited, i, j - 1);
            final long p2 = visited.containsKey(right) ? visited.get(right) : countDifferenceTimelines(input, visited, i, j + 1);

            visited.putIfAbsent(left, p1);
            visited.putIfAbsent(right, p2);

            return p1 + p2;
        } else {

            return countDifferenceTimelines(input, visited, i + 1, j);
        }
    }

    @Override
    protected String getDay() {

        return "day07";
    }
}
