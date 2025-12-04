package org.example.aoc.aoc2025;

import java.util.Objects;
import java.util.stream.Stream;

class Day04 extends AoC2025Day<char[][]> {

    static final char ROLL_OF_PAPER = '@';

    @Override
    protected char[][] parseInput(String strInput) {

        return strInput.lines()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    @Override
    protected Long partOne(char[][] input) {

        return traverseAndRemovableRolls(input, false);
    }

    @Override
    protected Long partTwo(char[][] input) {

        long removableRolls = 0;

        long z = traverseAndRemovableRolls(input, true);

        while (z != 0) {
            removableRolls += z;
            z = traverseAndRemovableRolls(input, true);
        }

        return removableRolls;
    }

    private long traverseAndRemovableRolls(char[][] input, boolean markAsRemoved) {

        long removableRolls = 0;

        for (int i = 0; i < input.length; i++) {
            final char[] row = input[i];

            for (int j = 0; j < row.length; j++) {
                final char cell = row[j];

                if (cell == ROLL_OF_PAPER) {

                    final long nRollsOfPaper = countAdjacentRollOfPaper(input, i, j);

                    if (nRollsOfPaper < 4) {
                        removableRolls++;

                        if (markAsRemoved) {

                            row[j] = '.';
                        }
                    }
                }
            }
        }

        return removableRolls;
    }

    private long countAdjacentRollOfPaper(char[][] layout, int i, int j) {

        return Stream.of(getCharacter(layout, i - 1, j - 1),
                         getCharacter(layout, i - 1, j),
                         getCharacter(layout, i - 1, j + 1),
                         getCharacter(layout, i, j - 1),
                         getCharacter(layout, i, j + 1),
                         getCharacter(layout, i + 1, j - 1),
                         getCharacter(layout, i + 1, j),
                         getCharacter(layout, i + 1, j + 1))
                .filter(Objects::nonNull)
                .filter(c -> ROLL_OF_PAPER == c)
                .count();
    }

    private Character getCharacter(char[][] layout, int i, int j) {

        if (i < 0 || i == layout.length || j < 0 || j == layout[i].length) {

            return null;
        }

        return layout[i][j];
    }

    @Override
    protected String getDay() {

        return "day04";
    }
}
