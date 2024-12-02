package org.example.aoc.aoc2022;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

class Day14 extends AoC2022Day<List<List<Day14.Position>>> {

    protected record Position(int x, int y) {
    }

    private record SandFallReturn(Position position, boolean reachedVoid, boolean reachedSandOrRock) {
    }

    @Override
    protected List<List<Day14.Position>> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> Arrays.stream(line.split(" -> "))
                        .map(s -> {
                            final String[] split = s.split(",");

                            return new Position(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                        })
                        .toList())
                .toList();
    }

    @Override
    protected Integer partOne(List<List<Day14.Position>> input) {

        final char[][] grid = buildGrid(input);

        final Position startSandPosition = new Position(500, 0);

        Position sandPosition = startSandPosition;
        int sandUnits = 0;

        while (true) {

            SandFallReturn sandFallReturn = getSandFall(grid, sandPosition);

            sandPosition = sandFallReturn.position;

            if (sandFallReturn.reachedVoid) {

                break;
            }

            if (sandFallReturn.reachedSandOrRock) {

                grid[sandPosition.y][sandPosition.x] = '+';
                sandPosition = startSandPosition;
                sandUnits++;
            }
        }

        return sandUnits;
    }

    private SandFallReturn getSandFall(char[][] grid, Position currentPosition) {

        SandFallReturn sandFallReturn = getPosition(grid, currentPosition.x, currentPosition.y + 1);

        if (sandFallReturn.reachedVoid || !sandFallReturn.reachedSandOrRock) {

            return sandFallReturn;
        }

        sandFallReturn = getPosition(grid, currentPosition.x - 1, currentPosition.y + 1);

        if (sandFallReturn.reachedVoid || !sandFallReturn.reachedSandOrRock) {

            return sandFallReturn;
        }

        sandFallReturn = getPosition(grid, currentPosition.x + 1, currentPosition.y + 1);

        if (sandFallReturn.reachedVoid || !sandFallReturn.reachedSandOrRock) {

            return sandFallReturn;
        }

        return new SandFallReturn(currentPosition, false, true);
    }

    private SandFallReturn getPosition(char[][] grid, int x, int y) {

        if (y < 0 || y == grid.length || x < 0 || x == grid[y].length) {

            return new SandFallReturn(null, true, false);
        }

        if (grid[y][x] != '\u0000') {

            return new SandFallReturn(null, false, true);
        }

        return new SandFallReturn(new Position(x, y), false, false);
    }

    private char[][] buildGrid(List<List<Day14.Position>> rocks) {

        final char[][] grid = new char[1000][1000];

        for (List<Position> rockPositions : rocks) {

            for (int i = 0; i < rockPositions.size() - 1; i++) {

                final Position start = rockPositions.get(i);
                final Position end = rockPositions.get(i + 1);

                IntStream.rangeClosed(Math.min(start.y, end.y), Math.max(start.y, end.y))
                        .forEach(y -> IntStream.rangeClosed(Math.min(start.x, end.x), Math.max(start.x, end.x))
                                .forEach(x -> grid[y][x] = '#'));
            }
        }

        return grid;
    }

    @Override
    protected Integer partTwo(List<List<Day14.Position>> input) {

        final char[][] grid = buildGrid(input);

        final int maxY = IntStream.range(0, 1000)
                .map(i -> 1000 - i - 1)
                .filter(i -> IntStream.range(0, 1000).anyMatch(j -> grid[i][j] == '#'))
                .findFirst()
                .orElseThrow();
        final int horizontalY = maxY + 2;

        final Position startSandPosition = new Position(500, 0);

        Position sandPosition = startSandPosition;
        int sandUnits = 0;

        while (true) {

            final SandFallReturn sandFallReturn = getSandFallNoVoidConsideration(grid, sandPosition, horizontalY);

            sandPosition = sandFallReturn.position;

            if (sandFallReturn.reachedSandOrRock) {

                sandUnits++;
                grid[sandPosition.y][sandPosition.x] = '+';

                if (sandPosition == startSandPosition) {

                    break;
                }

                sandPosition = startSandPosition;
            }
        }

        return sandUnits;
    }

    private SandFallReturn getSandFallNoVoidConsideration(char[][] grid, Position currentPosition, int horizontalY) {


        SandFallReturn sandFallReturn = getPositionNoVoidConsideration(grid, currentPosition.x, currentPosition.y + 1, horizontalY);

        if (!sandFallReturn.reachedSandOrRock) {

            return sandFallReturn;
        }

        sandFallReturn = getPositionNoVoidConsideration(grid, currentPosition.x - 1, currentPosition.y + 1, horizontalY);

        if (!sandFallReturn.reachedSandOrRock) {

            return sandFallReturn;
        }

        sandFallReturn = getPositionNoVoidConsideration(grid, currentPosition.x + 1, currentPosition.y + 1, horizontalY);

        if (!sandFallReturn.reachedSandOrRock) {

            return sandFallReturn;
        }

        return new SandFallReturn(currentPosition, false, true);
    }

    private SandFallReturn getPositionNoVoidConsideration(char[][] grid, int x, int y, int horizontalY) {

        if (y == horizontalY || grid[y][x] != '\u0000') {

            return new SandFallReturn(null, false, true);
        }

        return new SandFallReturn(new Position(x, y), false, false);
    }

    @Override
    protected String getDay() {

        return "day14";
    }
}
