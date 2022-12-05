package org.example.aoc.aoc2020;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Day11 extends AoC2020Day<Character[][]> {

    private record Position(int i, int j) {
    }

    @Override
    protected Character[][] parseInput(String strInput) {

        return strInput.lines()
                .map(line -> line.chars().mapToObj(c -> (char) c).toArray((Character[]::new)))
                .toArray(Character[][]::new);
    }

    @Override
    protected Long partOne(Character[][] input) {

        final Character[][] layout = Arrays.stream(input)
                .map(i -> Arrays.stream(i).toArray(Character[]::new))
                .toArray(Character[][]::new);

        boolean hasChanges = true;

        while (hasChanges) {

            final List<Position> positionsToChange = IntStream.range(0, layout.length)
                    .mapToObj(i -> IntStream.range(0, layout[i].length)
                            .filter(j -> {

                                final List<Character> adjacentSeats = getAdjacentSeats(layout, i, j);

                                return layout[i][j] == 'L' && !areAllSeatsFree(adjacentSeats) ||
                                        layout[i][j] == '#' && hasNOrMoreAdjacentSeatsOccupied(adjacentSeats, 4);
                            })
                            .mapToObj(j -> new Position(i, j)))
                    .flatMap(Function.identity())
                    .toList();

            for (Position position : positionsToChange) {

                if (layout[position.i][position.j] == 'L') {
                    layout[position.i][position.j] = '#';
                } else if (layout[position.i][position.j] == '#') {
                    layout[position.i][position.j] = 'L';
                }
            }

            hasChanges = !positionsToChange.isEmpty();
        }

        return countOccupiedSeats(input);
    }

    private List<Character> getAdjacentSeats(Character[][] layout, int i, int j) {

        return Stream.of(getCharacter(layout, i - 1, j - 1),
                         getCharacter(layout, i - 1, j),
                         getCharacter(layout, i - 1, j + 1),
                         getCharacter(layout, i, j - 1),
                         getCharacter(layout, i, j + 1),
                         getCharacter(layout, i + 1, j - 1),
                         getCharacter(layout, i + 1, j),
                         getCharacter(layout, i + 1, j + 1))
                .filter(Objects::nonNull)
                .toList();
    }

    private Character getCharacter(Character[][] layout, int i, int j) {

        if (i < 0 || i == layout.length || j < 0 || j == layout[i].length) {

            return null;
        }

        return layout[i][j];
    }

    @Override
    protected Long partTwo(Character[][] input) {

        boolean hasChanges = true;

        while (hasChanges) {

            final List<Position> positionsToChange = IntStream.range(0, input.length)
                    .mapToObj(i -> IntStream.range(0, input[i].length)
                            .filter(j -> {

                                final List<Character> firstSeatsSeen = getFirstSeatsSeen(input, i, j);

                                return input[i][j] == 'L' && areAllSeatsFree(firstSeatsSeen) ||
                                        input[i][j] == '#' && hasNOrMoreAdjacentSeatsOccupied(firstSeatsSeen, 5);
                            })
                            .mapToObj(j -> new Position(i, j)))
                    .flatMap(Function.identity())
                    .toList();

            for (Position position : positionsToChange) {

                if (input[position.i][position.j] == 'L') {
                    input[position.i][position.j] = '#';
                } else if (input[position.i][position.j] == '#') {
                    input[position.i][position.j] = 'L';
                }
            }

            hasChanges = !positionsToChange.isEmpty();
        }

        return countOccupiedSeats(input);
    }

    private boolean areAllSeatsFree(List<Character> seats) {

        return seats.stream().noneMatch(c -> c == '#');
    }

    private boolean hasNOrMoreAdjacentSeatsOccupied(List<Character> seats, int n) {

        return seats.stream().filter(c -> c == '#').count() >= n;
    }

    private List<Character> getFirstSeatsSeen(Character[][] input, int i, int j) {

        return Stream.of(getCharacters(input, i, j, ii -> ii - 1, jj -> jj - 1),
                         getCharacters(input, i, j, ii -> ii - 1, jj -> jj),
                         getCharacters(input, i, j, ii -> ii - 1, jj -> jj + 1),
                         getCharacters(input, i, j, ii -> ii, jj -> jj - 1),
                         getCharacters(input, i, j, ii -> ii, jj -> jj + 1),
                         getCharacters(input, i, j, ii -> ii + 1, jj -> jj - 1),
                         getCharacters(input, i, j, ii -> ii + 1, jj -> jj),
                         getCharacters(input, i, j, ii -> ii + 1, jj -> jj + 1))
                .filter(Objects::nonNull)
                .toList();
    }

    private Character getCharacters(Character[][] input,
                                    int i,
                                    int j,
                                    IntUnaryOperator iGenerator,
                                    IntUnaryOperator jGenerator) {

        i = iGenerator.applyAsInt(i);
        j = jGenerator.applyAsInt(j);

        while (i >= 0 && i < input.length && j >= 0 && j < input[i].length) {

            if (input[i][j] != '.') {

                return input[i][j];
            }

            i = iGenerator.applyAsInt(i);
            j = jGenerator.applyAsInt(j);
        }

        return null;
    }

    private Long countOccupiedSeats(Character[][] input) {

        return IntStream.range(0, input.length)
                .flatMap(i -> IntStream.range(0, input[i].length)
                        .filter(j -> input[i][j] == '#'))
                .count();
    }

    @Override
    protected String getDay() {

        return "day11";
    }
}
