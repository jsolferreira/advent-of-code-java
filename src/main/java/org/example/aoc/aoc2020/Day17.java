package org.example.aoc.aoc2020;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

class Day17 extends AoC2020Day<Day17.State[][][][]> {

    protected enum State {
        ACTIVE,
        INACTIVE
    }

    @Override
    protected Day17.State[][][][] parseInput(String strInput) {

        final State[][][][] s = new State[1][1][][];

        final State[][] states = strInput.lines()
                .map(line -> line.chars()
                        .mapToObj(c -> (char) c == '.' ? State.INACTIVE : State.ACTIVE)
                        .toArray(State[]::new)
                )
                .toArray(State[][]::new);

        s[0][0] = states;

        return s;
    }

    @Override
    protected Long partOne(State[][][][] input) {

        final State[][][][] layout = Arrays.stream(input)
                .map(w -> Arrays.stream(w)
                        .map(z -> Arrays.stream(z)
                                .map(y -> Arrays.stream(y)
                                        .toArray(State[]::new))
                                .toArray(State[][]::new))
                        .toArray(State[][][]::new))
                .toArray(State[][][][]::new);

        State[][][] inputExpanded = expandZPlane(layout[0]);

        for (int i = 0; i < 6; i++) {

            final State[][][] finalInputExpanded = inputExpanded;
            final State[][][] states = IntStream.range(0, inputExpanded.length)
                    .mapToObj(z -> IntStream.range(0, finalInputExpanded[z].length)
                            .mapToObj(y -> IntStream.range(0, finalInputExpanded[z][y].length)
                                    .mapToObj(x -> {

                                        final List<State> adjacentStates = getAdjacentStates(finalInputExpanded, x, y, z);

                                        return getNextState(adjacentStates, finalInputExpanded[z][y][x]);
                                    })
                                    .toArray(State[]::new)
                            )
                            .toArray(State[][]::new)
                    )
                    .toArray(State[][][]::new);

            inputExpanded = expandZPlane(states);
        }

        return Arrays.stream(inputExpanded)
                .mapToLong(z -> Arrays.stream(z)
                        .mapToLong(y -> Arrays.stream(y)
                                .filter(x -> x == State.ACTIVE)
                                .count()
                        ).sum())
                .sum();
    }

    private State[][][][] expandWPlane(State[][][][] input) {

        final State[][][][] states = Arrays.stream(input)
                .map(this::expandZPlane)
                .toArray(State[][][][]::new);

        final State[][][][] newS = new State[states.length + 2][][][];

        for (int i = 0; i < newS.length; i++) {

            newS[i] = newZ(states[0].length, states[0][0].length, states[0][0][0].length);
        }

        System.arraycopy(states, 0, newS, 1, states.length);

        return newS;
    }

    private State[][][] expandZPlane(State[][][] input) {

        final State[][][] states = Arrays.stream(input)
                .map(this::expandYPlane)
                .toArray(State[][][]::new);

        final State[][][] newS = new State[states.length + 2][][];

        for (int i = 0; i < newS.length; i++) {

            newS[i] = newInactiveColumns(states[0].length, states[0][0].length);
        }

        System.arraycopy(states, 0, newS, 1, states.length);

        return newS;
    }

    private State[][] expandYPlane(State[][] columns) {

        final State[][] columnsWithRowsExpanded = Arrays.stream(columns)
                .map(this::expandXPlane)
                .toArray(State[][]::new);

        final State[][] newColumns = newInactiveColumns(columns.length + 2, columnsWithRowsExpanded[0].length);

        System.arraycopy(columnsWithRowsExpanded, 0, newColumns, 1, columnsWithRowsExpanded.length);

        return newColumns;
    }

    private State[] expandXPlane(State[] row) {

        final State[] newRow = newInactiveRow(row.length + 2);
        System.arraycopy(row, 0, newRow, 1, row.length);

        return newRow;
    }

    private State[][][] newZ(int length1, int length2, int length3) {

        final State[][][] newColumns = new State[length1][][];

        for (int i = 0; i < newColumns.length; i++) {

            newColumns[i] = newInactiveColumns(length2, length3);
        }

        return newColumns;
    }

    private State[][] newInactiveColumns(int length1, int length2) {

        final State[][] newColumns = new State[length1][];

        for (int i = 0; i < newColumns.length; i++) {

            newColumns[i] = newInactiveRow(length2);
        }

        return newColumns;
    }

    private State[] newInactiveRow(int length) {

        final State[] newRow = new State[length];
        Arrays.fill(newRow, State.INACTIVE);

        return newRow;
    }

    private List<State> getAdjacentStates(State[][][] layout, int x, int y, int z) {

        return IntStream.rangeClosed(x - 1, x + 1)
                .boxed()
                .flatMap(ix -> IntStream.rangeClosed(y - 1, y + 1)
                        .boxed()
                        .flatMap(iy -> IntStream.rangeClosed(z - 1, z + 1)
                                .filter(iz -> ix != x || iy != y || iz != z)
                                .mapToObj(iz -> getState(layout, ix, iy, iz))
                                .filter(Objects::nonNull)))
                .toList();
    }

    private State getState(State[][][] layout, int x, int y, int z) {

        if (z < 0 || z == layout.length || y < 0 || y == layout[z].length || x < 0 || x == layout[z][y].length) {

            return null;
        }

        return layout[z][y][x];
    }

    @Override
    protected Long partTwo(State[][][][] input) {

        State[][][][] inputExpanded = expandWPlane(input);

        for (int i = 0; i < 6; i++) {

            final State[][][][] finalInputExpanded = inputExpanded;
            final State[][][][] states = IntStream.range(0, inputExpanded.length)
                    .mapToObj(w -> IntStream.range(0, finalInputExpanded[w].length)
                            .mapToObj(z -> IntStream.range(0, finalInputExpanded[w][z].length)
                                    .mapToObj(y -> IntStream.range(0, finalInputExpanded[w][z][y].length)
                                            .mapToObj(x -> {

                                                final List<State> adjacentStates = getAdjacentStatesWPlane(finalInputExpanded, x, y, z, w);

                                                return getNextState(adjacentStates, finalInputExpanded[w][z][y][x]);
                                            })
                                            .toArray(State[]::new)
                                    )
                                    .toArray(State[][]::new)
                            )
                            .toArray(State[][][]::new)
                    )
                    .toArray(State[][][][]::new);

            inputExpanded = expandWPlane(states);
        }

        return Arrays.stream(inputExpanded)
                .mapToLong(w -> Arrays.stream(w)
                        .mapToLong(z -> Arrays.stream(z)
                                .mapToLong(y -> Arrays.stream(y)
                                        .filter(x -> x == State.ACTIVE)
                                        .count()
                                )
                                .sum())
                        .sum())
                .sum();
    }

    private List<State> getAdjacentStatesWPlane(State[][][][] layout, int x, int y, int z, int w) {

        return IntStream.rangeClosed(x - 1, x + 1)
                .boxed()
                .flatMap(ix -> IntStream.rangeClosed(y - 1, y + 1)
                        .boxed()
                        .flatMap(iy -> IntStream.rangeClosed(z - 1, z + 1)
                                .boxed()
                                .flatMap(iz -> IntStream.rangeClosed(w - 1, w + 1)
                                        .filter(iw -> ix != x || iy != y || iz != z || iw != w)
                                        .mapToObj(iw -> getStateWPlane(layout, ix, iy, iz, iw))
                                        .filter(Objects::nonNull))))
                .toList();
    }

    private State getStateWPlane(State[][][][] layout, int x, int y, int z, int w) {

        if (w < 0 || w == layout.length
                || z < 0 || z == layout[w].length
                || y < 0 || y == layout[w][z].length
                || x < 0 || x == layout[w][z][y].length) {

            return null;
        }

        return layout[w][z][y][x];
    }

    private State getNextState(List<State> adjacentStates, State currentState) {

        long count = adjacentStates.stream()
                .filter(state -> state == State.ACTIVE)
                .count();

        if (currentState == State.ACTIVE) {

            if (count == 2 || count == 3) {
                return State.ACTIVE;
            } else {
                return State.INACTIVE;
            }
        } else {
            if (count == 3) {
                return State.ACTIVE;
            } else {
                return State.INACTIVE;
            }
        }
    }

    @Override
    protected String getDay() {

        return "day17";
    }
}
