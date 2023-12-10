package org.example.aoc.aoc2023;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Day10 extends AoC2023Day<Day10.Cell[][]> {

    protected record Cell(char pipe, int distance) {
    }

    private record Position(int i, int j) {
    }

    @Override
    protected Cell[][] parseInput(String strInput) {

        return strInput.lines()
                .map(line -> line.chars()
                        .mapToObj(c -> new Cell((char) c, -1))
                        .toArray(Cell[]::new))
                .toArray(Cell[][]::new);
    }

    @Override
    protected Integer partOne(Cell[][] input) {

        final List<Position> loop = getLoop(input);

        return loop.size() / 2;
    }

    @Override
    protected Integer partTwo(Cell[][] input) {

        final List<Position> loop = getLoop(input);

        final List<Position> reorderedLoop = getLoopStartingAtTopMostPosition(input, loop);

        final List<Position> enclosed = new ArrayList<>();
        final List<Character> inSequence = List.of('F', '7', 'J', 'L');
        final List<Character> outSequence = List.of('L', 'J', '7', 'F');

        int nSequence = 0;
        boolean visit = true;

        for (Position position : reorderedLoop) {

            final Cell cell = input[position.i][position.j];

            char pipe = cell.pipe;

            if (visit) {
                if (inSequence.get(nSequence) != pipe) {

                    visit = false;
                    nSequence = outSequence.indexOf(pipe);
                }
            } else {
                if (outSequence.get(nSequence) != pipe) {

                    visit = true;
                    nSequence = inSequence.indexOf(pipe);
                }
            }

            nSequence = (nSequence + 1) % 4;

            if (visit) {
                if (pipe == 'F') {

                    visitCellRecursively(input, position, loop, enclosed, 1, 1);
                } else if (pipe == 'L') {

                    visitCellRecursively(input, position, loop, enclosed, -1, 1);
                } else if (pipe == 'J') {

                    visitCellRecursively(input, position, loop, enclosed, -1, -1);
                } else if (pipe == '7') {

                    visitCellRecursively(input, position, loop, enclosed, 1, -1);
                }
            } else {
                if (pipe == 'F') {

                    visitCellRecursively(input, position, loop, enclosed, -1, -1);
                } else if (pipe == 'L') {

                    visitCellRecursively(input, position, loop, enclosed, 1, -1);
                } else if (pipe == 'J') {

                    visitCellRecursively(input, position, loop, enclosed, 1, 1);
                } else if (pipe == '7') {

                    visitCellRecursively(input, position, loop, enclosed, -1, 1);
                }
            }
        }

        return enclosed.size();
    }

    private List<Position> getLoop(Cell[][] input) {

        final Position startPosition = getStartPosition(input);

        final List<Position> loop = new ArrayList<>();
        loop.add(startPosition);

        Position currentPosition = checkAdjacency(input, startPosition.i, startPosition.j);
        Position previousPosition = startPosition;

        while (currentPosition.i != startPosition.i || currentPosition.j != startPosition.j) {

            final Cell cell = input[currentPosition.i][currentPosition.j];

            loop.add(new Position(currentPosition.i, currentPosition.j));

            final char pipe = cell.pipe;
            if (pipe == '|' || pipe == '-') {
                Position aux = previousPosition;
                previousPosition = currentPosition;
                currentPosition = new Position(currentPosition.i + (currentPosition.i - aux.i),
                                               currentPosition.j + (currentPosition.j - aux.j));
            } else if (pipe == 'L') {
                Position aux = previousPosition;
                previousPosition = currentPosition;
                currentPosition = new Position(currentPosition.i - 1 + (currentPosition.i - aux.i),
                                               currentPosition.j + 1 - Math.abs(currentPosition.j - aux.j));
            } else if (pipe == 'J') {
                Position aux = previousPosition;
                previousPosition = currentPosition;
                currentPosition = new Position(currentPosition.i - 1 + Math.abs(currentPosition.i - aux.i),
                                               currentPosition.j - 1 + (currentPosition.j - aux.j));
            } else if (pipe == 'F') {
                Position aux = previousPosition;
                previousPosition = currentPosition;
                currentPosition = new Position(currentPosition.i + 1 - Math.abs(currentPosition.i - aux.i),
                                               currentPosition.j + 1 - Math.abs(currentPosition.j - aux.j));
            } else if (pipe == '7') {
                Position aux = previousPosition;
                previousPosition = currentPosition;
                currentPosition = new Position(currentPosition.i + 1 - Math.abs(currentPosition.i - aux.i),
                                               currentPosition.j - 1 + (currentPosition.j - aux.j));
            }
        }

        return loop;
    }

    private Position checkAdjacency(Cell[][] input, int i, int j) {

        return Stream.of(
                        getPosition(input, i, j + 1, Set.of('-', '7', 'J')),
                        getPosition(input, i + 1, j, Set.of('|', 'L', 'J')),
                        getPosition(input, i, j - 1, Set.of('-', 'L', 'F')),
                        getPosition(input, i - 1, j, Set.of('|', 'F', '7')))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow();
    }

    private Position getPosition(Cell[][] input, int i, int j, Set<Character> possibleDirections) {

        if (i < 0 || i >= input.length || j < 0 || j >= input[i].length) {

            return null;
        }

        if (possibleDirections.contains(input[i][j].pipe)) {
            return new Position(i, j);
        } else {
            return null;
        }
    }

    private List<Position> getLoopStartingAtTopMostPosition(Cell[][] input, List<Position> loop) {

        final Position firstPosition = loop
                .stream()
                .min(Comparator.<Position, Integer>comparing(position -> (position).i).thenComparing(position -> (position).j))
                .orElseThrow();

        return Stream.concat(
                        loop.subList(loop.indexOf(firstPosition), loop.size()).stream(),
                        loop.subList(0, loop.indexOf(firstPosition)).stream())
                .filter(position -> input[position.i][position.j].pipe != '|' && input[position.i][position.j].pipe != '-')
                .toList();
    }

    private void visitCellRecursively(Cell[][] input,
                                      Position currentPosition,
                                      List<Position> loop,
                                      List<Position> enclosedPositions,
                                      int i,
                                      int j) {

        final Position diagonalCell = new Position(currentPosition.i + i, currentPosition.j + j);
        final Position verticalCell = new Position(currentPosition.i + i, currentPosition.j);
        final Position horizontalCell = new Position(currentPosition.i, currentPosition.j + j);

        if (addEnclosed(input, diagonalCell, loop, enclosedPositions)) {

            visitCellRecursively(input, diagonalCell, loop, enclosedPositions, i, j);
        }

        if (addEnclosed(input, verticalCell, loop, enclosedPositions)) {

            visitCellRecursively(input, verticalCell, loop, enclosedPositions, i, j);
        }

        if (addEnclosed(input, horizontalCell, loop, enclosedPositions)) {

            visitCellRecursively(input, horizontalCell, loop, enclosedPositions, i, j);
        }
    }

    private boolean addEnclosed(Cell[][] input,
                                Position position,
                                List<Position> loop,
                                List<Position> enclosedPositions) {

        if (position.i < 0 || position.i >= input.length || position.j < 0 || position.j >= input[position.i].length) {
            return false;
        }

        if (enclosedPositions.contains(position) || loop.contains(position)) {
            return false;
        }

        enclosedPositions.add(position);

        return true;
    }

    private Position getStartPosition(Cell[][] input) {

        return IntStream.range(0, input.length)
                .boxed()
                .flatMap(i -> IntStream.range(0, input[i].length)
                        .boxed()
                        .filter(j -> input[i][j].pipe == 'S')
                        .map(j -> new Position(i, j)))
                .findFirst()
                .orElseThrow();
    }

    @Override
    protected String getDay() {

        return "day10";
    }
}
