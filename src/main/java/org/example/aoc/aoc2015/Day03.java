package org.example.aoc.aoc2015;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Day03 extends AoC2015Day<List<Day03.Direction>> {

    protected enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    private record Position(int i, int j) {
    }

    @Override
    protected List<Direction> parseInput(String strInput) {

        return strInput.lines()
                .flatMap(line -> line.chars()
                        .mapToObj(c -> switch (c) {
                            case '^' -> Direction.NORTH;
                            case 'v' -> Direction.SOUTH;
                            case '>' -> Direction.EAST;
                            case '<' -> Direction.WEST;
                            default -> throw new RuntimeException();
                        }))
                .toList();
    }

    @Override
    protected Integer partOne(List<Direction> input) {

        Position position = new Position(0, 0);
        final Set<Position> visitedPositions = new HashSet<>();
        visitedPositions.add(position);

        for (Direction direction : input) {

            position = switch (direction) {
                case NORTH -> new Position(position.i + 1, position.j);
                case SOUTH -> new Position(position.i - 1, position.j);
                case EAST -> new Position(position.i, position.j - 1);
                case WEST -> new Position(position.i, position.j + 1);
            };

            visitedPositions.add(position);
        }
        

        return visitedPositions.size();
    }

    @Override
    protected Integer partTwo(List<Direction> input) {

        return null;
    }

    @Override
    protected String getDay() {

        return "day03";
    }
}
