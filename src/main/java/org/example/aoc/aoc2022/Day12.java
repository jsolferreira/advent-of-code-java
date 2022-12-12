package org.example.aoc.aoc2022;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Day12 extends AoC2022Day<Character[][]> {

    private record Position(int i, int j) {}

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

        final Position startingPosition = getStartingPosition(input);
        final Position endingPosition = getEndingPosition(input);

        return dijkstra(input, startingPosition, endingPosition);
    }

    @Override
    protected Integer partTwo(Character[][] input) {

        final Position endingPosition = getEndingPosition(input);

        return IntStream.range(0, input.length)
                .boxed()
                .flatMap(i -> IntStream.range(0, input[i].length)
                        .filter(j -> input[i][j] == 'a' || input[i][j] == 'S')
                        .mapToObj(j -> new Position(i, j)))
                .map(startingPosition -> dijkstra(input, startingPosition, endingPosition))
                .filter(Objects::nonNull)
                .sorted()
                .findFirst()
                .orElseThrow();
    }

    private Position getStartingPosition(Character[][] input) {

        return getPositionByValue(input, 'S');
    }

    private Position getEndingPosition(Character[][] input) {

        return getPositionByValue(input, 'E');
    }

    private Position getPositionByValue(Character[][] input, char value) {

        return IntStream.range(0, input.length)
                .boxed()
                .flatMap(i -> IntStream.range(0, input[i].length)
                        .filter(j -> input[i][j] == value)
                        .mapToObj(j -> new Position(i, j)))
                .findFirst()
                .orElseThrow();
    }

    private Integer dijkstra(Character[][] input, Position startingPosition, Position endingPosition) {

        final HashSet<Position> visitedPoints = new HashSet<>();
        final HashMap<Position, Integer> shortestDistance = new HashMap<>();
        shortestDistance.put(startingPosition, 0);
        final PriorityQueue<Position> unvisitedPoints = new PriorityQueue<>(Comparator.comparingInt(shortestDistance::get));

        Position p = startingPosition;
        while (!p.equals(endingPosition)) {

            p = unvisitedPoints.stream().findFirst().orElse(startingPosition);
            final int distance = shortestDistance.get(p);

            final List<Position> adjacentPoints = getPossibleMoves(input, p);

            for (Position adjacentPoint : adjacentPoints) {
                if (!visitedPoints.contains(adjacentPoint)) {
                    shortestDistance.merge(adjacentPoint, distance + 1, (v1, v2) -> v1 > v2 ? v2 : v1);

                    if (!unvisitedPoints.contains(adjacentPoint)) {
                        unvisitedPoints.add(adjacentPoint);
                    }
                }
            }

            visitedPoints.add(p);
            unvisitedPoints.remove(p);

            if (unvisitedPoints.isEmpty()) {

                break;
            }
        }

        return shortestDistance.get(endingPosition);
    }

    private List<Position> getPossibleMoves(Character[][] input, Position initialPosition) {

        return Stream.of(
                        getPosition(input, initialPosition.i - 1, initialPosition.j),
                        getPosition(input, initialPosition.i + 1, initialPosition.j),
                        getPosition(input, initialPosition.i, initialPosition.j - 1),
                        getPosition(input, initialPosition.i, initialPosition.j + 1)
                )
                .filter(Objects::nonNull)
                .filter(position -> getPositionDifference(input, initialPosition, position) <= 1)
                .toList();
    }

    private Position getPosition(Character[][] input, int i, int j) {

        if (i < 0 || i == input.length || j < 0 || j == input[i].length) {

            return null;
        }

        return new Position(i, j);
    }

    private int getPositionDifference(Character[][] input, Position position1, Position position2) {

        return getValueFromPosition(input, position2) - getValueFromPosition(input, position1);
    }

    private int getValueFromPosition(Character[][] input, Position position) {

        final char value = input[position.i][position.j];

        if (value == 'S') {

            return 'a';
        } else if (value == 'E') {

            return 'z';
        } else {

            return value;
        }
    }

    @Override
    protected String getDay() {

        return "day12";
    }
}
