package org.example.aoc.aoc2016;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Day01 extends AoC2016Day<List<Day01.Instruction>> {

    private enum Direction {
        R,
        L
    }

    private enum Cardinal {
        N,
        E,
        S,
        W;

        public Cardinal rotate(Direction direction) {
            return switch (direction) {
                case R -> switch (this) {
                    case N -> Cardinal.E;
                    case E -> Cardinal.S;
                    case S -> Cardinal.W;
                    case W -> Cardinal.N;
                };
                case L -> switch (this) {
                    case N -> Cardinal.W;
                    case E -> Cardinal.N;
                    case S -> Cardinal.E;
                    case W -> Cardinal.S;
                };
            };
        }
    }

    protected record Instruction(Direction direction, int nBlocks) {
    }

    private record Location(int i, int j) {
    }

    @Override
    protected List<Instruction> parseInput(String strInput) {

        return Arrays.stream(strInput.split(", "))
                .map(i -> new Instruction(Direction.valueOf(i.substring(0, 1)),
                                          Integer.parseInt(i.substring(1))))
                .toList();
    }

    @Override
    protected Long partOne(List<Instruction> input) {

        int i = 0;
        int j = 0;
        Cardinal facing = Cardinal.N;

        for (Instruction instruction : input) {

            facing = facing.rotate(instruction.direction);

            switch (facing) {
                case N -> j += instruction.nBlocks;
                case E -> i += instruction.nBlocks;
                case S -> j -= instruction.nBlocks;
                case W -> i -= instruction.nBlocks;
            }
        }

        return (long) Math.abs(i) + Math.abs(j);
    }

    @Override
    protected Long partTwo(List<Instruction> input) {

        final HashSet<Location> visitedLocations = new HashSet<>();

        int i = 0;
        int j = 0;
        Cardinal facing = Cardinal.N;

        visitedLocations.add(new Location(i, j));

        for (Instruction instruction : input) {

            facing = facing.rotate(instruction.direction);

            switch (facing) {
                case N -> {
                    int blocks = 1;

                    while (blocks <= instruction.nBlocks) {

                        if (checkIfLocationIsAlreadyVisited(visitedLocations, i, ++j)) {

                            return (long) Math.abs(i) + Math.abs(j);
                        }

                        blocks++;
                    }
                }
                case E -> {
                    int blocks = 1;

                    while (blocks <= instruction.nBlocks) {

                        if (checkIfLocationIsAlreadyVisited(visitedLocations, ++i, j)) {

                            return (long) Math.abs(i) + Math.abs(j);
                        }

                        blocks++;
                    }
                }
                case S -> {
                    int blocks = 1;

                    while (blocks <= instruction.nBlocks) {

                        if (checkIfLocationIsAlreadyVisited(visitedLocations, i, --j)) {

                            return (long) Math.abs(i) + Math.abs(j);
                        }

                        blocks++;
                    }
                }
                case W -> {
                    int blocks = 1;

                    while (blocks <= instruction.nBlocks) {

                        if (checkIfLocationIsAlreadyVisited(visitedLocations, --i, j)) {

                            return (long) Math.abs(i) + Math.abs(j);
                        }

                        blocks++;
                    }
                }
            }
        }

        return (long) Math.abs(i) + Math.abs(j);
    }

    private boolean checkIfLocationIsAlreadyVisited(Set<Location> visitedLocations, int i, int j) {

        final Location location = new Location(i, j);

        if (visitedLocations.contains(location)) {

            return true;
        } else {

            visitedLocations.add(location);
        }

        return false;
    }

    @Override
    protected String getDay() {

        return "day01";
    }
}
