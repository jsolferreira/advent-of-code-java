package org.example.aoc.aoc2019;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Day03 extends AoC2019Day<Day03.Wires> {

    private enum Direction {
        R,
        L,
        D,
        U
    }

    private record Instruction(Direction direction, int n) {}

    protected record Wires(List<Instruction> firstWire, List<Instruction> secondWire) {}

    private record Position(int x, int y) {}

    @Override
    protected Wires parseInput(String strInput) {

        final String[] split = strInput.split(System.lineSeparator());

        return new Wires(parseWire(split[0]), parseWire(split[1]));
    }

    private List<Instruction> parseWire(String wire) {

        return Arrays.stream(wire.split(","))
                .map(i -> new Instruction(Direction.valueOf(i.substring(0, 1)),
                                          Integer.parseInt(i.substring(1))))
                .toList();
    }

    @Override
    protected Long partOne(Wires input) {

        final Set<Position> positions = new HashSet<>();

        int x = 0;
        int y = 0;

        for (Instruction instruction : input.firstWire) {

            for (int i = 0; i < instruction.n; i++) {

                final Position position = new Position(x, y);

                positions.add(position);

                switch (instruction.direction) {
                    case R -> x++;
                    case L -> x--;
                    case U -> y++;
                    case D -> y--;
                }
            }
        }

        x = 0;
        y = 0;

        long min = Long.MAX_VALUE;

        for (Instruction instruction : input.secondWire) {

            for (int i = 0; i < instruction.n; i++) {

                final Position position = new Position(x, y);

                if (positions.contains(position)) {

                    int distance = Math.abs(position.x) + Math.abs(position.y);

                    if (distance < min && distance != 0) {
                        min = distance;
                    }
                }

                switch (instruction.direction) {
                    case R -> x++;
                    case L -> x--;
                    case U -> y++;
                    case D -> y--;
                }
            }
        }

        return min;
    }

    @Override
    protected Long partTwo(Wires input) {

        final HashMap<Position, Integer> grid = new HashMap<>();

        int x = 0;
        int y = 0;
        int steps = 0;

        for (Instruction instruction : input.firstWire) {

            for (int i = 0; i < instruction.n; i++) {

                final Position position = new Position(x, y);

                grid.put(position, steps);

                switch (instruction.direction) {
                    case R -> x++;
                    case L -> x--;
                    case U -> y++;
                    case D -> y--;
                }

                steps++;
            }
        }

        x = 0;
        y = 0;
        steps = 0;

        long min = Long.MAX_VALUE;

        for (Instruction instruction : input.secondWire) {

            for (int i = 0; i < instruction.n; i++) {

                final Position position = new Position(x, y);

                if (grid.containsKey(position)) {

                    int totalSteps = steps + grid.get(position);

                    if (totalSteps < min && totalSteps != 0) {
                        min = totalSteps;
                    }
                }

                switch (instruction.direction) {
                    case R -> x++;
                    case L -> x--;
                    case U -> y++;
                    case D -> y--;
                }

                steps++;
            }
        }

        return min;
    }

    @Override
    protected String getDay() {

        return "day03";
    }
}
