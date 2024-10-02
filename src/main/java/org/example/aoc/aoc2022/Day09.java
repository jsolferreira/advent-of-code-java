package org.example.aoc.aoc2022;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Day09 extends AoC2022Day<List<Day09.Motion>> {

    private enum Direction {
        R,
        L,
        U,
        D
    }

    protected record Motion(Direction direction, int steps) {
    }

    private record Position(int i, int j) {
    }

    @Override
    protected List<Motion> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> {
                    final String[] split = line.split(" ");

                    final Direction direction = Direction.valueOf(split[0]);
                    final int steps = Integer.parseInt(split[1]);

                    return new Motion(direction, steps);
                })
                .toList();
    }

    @Override
    protected Long partOne(List<Motion> input) {

        final List<Position> rope = new ArrayList<>();

        rope.add(new Position(0, 0));
        rope.add(new Position(0, 0));

        return moveAndCountTailVisitedPositions(rope, input);
    }

    @Override
    protected Long partTwo(List<Motion> input) {

        final List<Position> rope = new ArrayList<>();

        rope.add(new Position(0, 0));
        rope.add(new Position(0, 0));
        rope.add(new Position(0, 0));
        rope.add(new Position(0, 0));
        rope.add(new Position(0, 0));
        rope.add(new Position(0, 0));
        rope.add(new Position(0, 0));
        rope.add(new Position(0, 0));
        rope.add(new Position(0, 0));
        rope.add(new Position(0, 0));

        return moveAndCountTailVisitedPositions(rope, input);
    }

    private long moveAndCountTailVisitedPositions(List<Position> rope, List<Motion> motions) {

        final Set<Position> visitedPositions = new HashSet<>();
        visitedPositions.add(rope.getLast());

        for (Motion motion : motions) {
            for (int i = 0; i < motion.steps; i++) {

                moveHead(rope, motion.direction);
                moveRemainingRope(rope, 1, visitedPositions);
            }
        }

        return visitedPositions.size();
    }

    final void moveHead(List<Position> rope, Direction direction) {

        final Position headPosition = rope.getFirst();

        rope.set(0, move(headPosition, direction));
    }

    final void moveRemainingRope(List<Position> rope, int index, Set<Position> visitedPositions) {

        final Position previousKnotPosition = rope.get(index - 1);
        final Position currentKnotPosition = rope.get(index);

        if (areKnotsSeparated(previousKnotPosition, currentKnotPosition)) {

            final Position knotPosition = moveCloserToKnot(previousKnotPosition, currentKnotPosition);

            rope.set(index, knotPosition);

            if (index == rope.size() - 1) {

                visitedPositions.add(knotPosition);
            } else {

                moveRemainingRope(rope, index + 1, visitedPositions);
            }
        }
    }

    private boolean areKnotsSeparated(Position position1, Position position2) {

        return Math.abs(position1.i - position2.i) > 1 || Math.abs(position1.j - position2.j) > 1;
    }

    private Position move(Position p, Direction direction) {

        return switch (direction) {
            case R -> new Position(p.i, p.j + 1);
            case L -> new Position(p.i, p.j - 1);
            case U -> new Position(p.i + 1, p.j);
            case D -> new Position(p.i - 1, p.j);
        };
    }

    private Position moveCloserToKnot(Position furtherPosition, Position position2) {

        final Position directionVector = new Position(Integer.compare(furtherPosition.i - position2.i, 0),
                                                      Integer.compare(furtherPosition.j - position2.j, 0));

        return new Position(position2.i + directionVector.i, position2.j + directionVector.j);
    }

    @Override
    protected String getDay() {

        return "day09";
    }
}
