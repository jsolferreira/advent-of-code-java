package org.example.aoc.aoc2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

class Day08 extends AoC2024Day<Character[][]> {

    record Position(int i, int j) {}

    @Override
    protected Character[][] parseInput(String strInput) {

        return strInput.lines()
                .map(line -> line.chars()
                        .mapToObj(c -> (char) c)
                        .toArray(Character[]::new))
                .toArray(Character[][]::new);
    }

    @Override
    protected Integer partOne(Character[][] input) {

        final Map<Character, List<Position>> antennas = new HashMap<>();

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                final char c = input[i][j];
                if (c != '.') {
                    final Position position = new Position(i, j);
                    if (antennas.containsKey(c)) {
                        List<Position> positions = antennas.get(c);
                        positions.add(position);
                        antennas.put(c, positions);
                    } else {
                        ArrayList<Position> value = new ArrayList<>();
                        value.add(position);
                        antennas.put(c, value);
                    }
                }
            }
        }

        final ArrayList<Position> antinodes = new ArrayList<>();

        for (List<Position> positions : antennas.values()) {
            for (int i = 0; i < positions.size() - 1; i++) {
                for (int j = i + 1; j < positions.size(); j++) {
                    final Position position1 = positions.get(i);
                    final Position position2 = positions.get(j);

                    final Position antinode1 = add(position1, diff(position1, position2));
                    final Position antinode2 = add(position2, diff(position2, position1));

                    if (inBounds(input, antinode1) && input[antinode1.i()][antinode1.j()] == '.') {
                        antinodes.add(antinode1);
                    }

                    if (inBounds(input, antinode2) && input[antinode2.i()][antinode2.j()] == '.') {
                        antinodes.add(antinode2);
                    }
                }
            }
        }

        return antinodes.size();
    }

    private Position diff(Position p1, Position p2) {

        return new Position(p1.i - p2.i, p1.j - p2.j);
    }

    private Position add(Position position1, Position position2) {

        return new Position(position1.i + position2.i, position1.j + position2.j);
    }

    private boolean inBounds(Character[][] grid, Position position) {

        return position.i() >= 0 && position.i() < grid.length && position.j() >= 0 && position.j() < grid[position.i()].length;
    }

    @Override
    protected Integer partTwo(Character[][] input) {

        final Map<Character, List<Position>> antennas = new HashMap<>();

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                final char c = input[i][j];
                if (c != '.') {
                    final Position position = new Position(i, j);
                    if (antennas.containsKey(c)) {
                        List<Position> positions = antennas.get(c);
                        positions.add(position);
                        antennas.put(c, positions);
                    } else {
                        ArrayList<Position> value = new ArrayList<>();
                        value.add(position);
                        antennas.put(c, value);
                    }
                }
            }
        }

        final HashSet<Position> antinodes = new HashSet<>();

        for (List<Position> positions : antennas.values()) {
            for (int i = 0; i < positions.size() - 1; i++) {
                for (int j = i + 1; j < positions.size(); j++) {
                    final Position position1 = positions.get(i);
                    final Position position2 = positions.get(j);

                    antinodes.add(position1);
                    antinodes.add(position2);

                    Position antinode1 = add(position1, diff(position1, position2));
                    Position antinode2 = add(position2, diff(position2, position1));

                    while (inBounds(input, antinode1)) {
                        antinodes.add(antinode1);
                        antinode1 = add(antinode1, diff(position1, position2));
                    }

                    while (inBounds(input, antinode2)) {
                        antinodes.add(antinode2);
                        antinode2 = add(antinode2, diff(position2, position1));
                    }
                }
            }
        }

        return antinodes.size();
    }

    @Override
    protected String getDay() {

        return "day08";
    }
}
