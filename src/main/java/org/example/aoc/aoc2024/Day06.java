package org.example.aoc.aoc2024;

import java.util.HashSet;
import java.util.Set;

class Day06 extends AoC2024Day<Character[][]> {

    enum Direction {
        UP(-1, 0, 'U'),
        DOWN(1, 0, 'D'),
        LEFT(0, -1, 'L'),
        RIGHT(0, 1, 'R');

        final int di;

        final int dj;

        final char c;

        Direction(int di, int dj, char c) {

            this.di = di;
            this.dj = dj;
            this.c = c;
        }

        public int getDi() {

            return di;
        }

        public int getDj() {

            return dj;
        }

        public char getC() {

            return c;
        }

        public Direction rotate() {

            return switch (this) {
                case UP -> RIGHT;
                case RIGHT -> DOWN;
                case DOWN -> LEFT;
                case LEFT -> UP;
            };
        }

        public boolean isOnRight(char c) {

            return switch (this) {
                case UP -> c == 'R';
                case RIGHT -> c == 'D';
                case DOWN -> c == 'L';
                case LEFT -> c == 'U';
            };
        }
    }

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

        Direction direction = Direction.UP;

        Position position = initialPosition(input);

        final Set<Position> positions = new HashSet<>();

        while (true) {
            final Position nextPosition = new Position(position.i + direction.getDi(), position.j + direction.getDj());

            if (!inBounds(input, nextPosition)) {
                break;
            }

            if (input[nextPosition.i][nextPosition.j] == '#') {
                direction = direction.rotate();
            } else {
                positions.add(position);
                position = nextPosition;
            }
        }

        positions.add(position);

        return positions.size();
    }

    private Position initialPosition(Character[][] grid) {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '^') {
                    return new Position(i, j);
                }
            }
        }

        return null;
    }

    private boolean inBounds(Character[][] grid, Position position) {

        return position.i >= 0 && position.i < grid.length && position.j >= 0 && position.j < grid[position.i].length;
    }

    @Override
    protected Integer partTwo(Character[][] input) {

        Direction direction = Direction.UP;

        Position position = initialPosition(input);

        final Set<Position> positions = new HashSet<>();

        int c = 0;

        while (true) {
            final Position nextPosition = new Position(position.i + direction.getDi(), position.j + direction.getDj());

            if (!inBounds(input, nextPosition)) {
                break;
            }

            if (input[nextPosition.i][nextPosition.j] == '#') {
                direction = direction.rotate();
            } else {

                if (positions.contains(position)) {

                    if (direction.isOnRight(input[position.i][position.j])) {
                        c++;
                    }
                } else {
                    input[position.i][position.j] = direction.getC();
                }

                positions.add(position);
                position = nextPosition;
            }
        }

        positions.add(position);

        return positions.size();
    }

    @Override
    protected String getDay() {

        return "day06";
    }
}
