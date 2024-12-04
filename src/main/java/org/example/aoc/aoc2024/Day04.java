package org.example.aoc.aoc2024;

import java.util.List;
import java.util.stream.Stream;

class Day04 extends AoC2024Day<Character[][]> {

    private static final String XMAS = "XMAS";

    @Override
    protected Character[][] parseInput(String strInput) {

        return strInput.lines()
                .map(line -> line.chars().mapToObj(c -> (char) c).toArray((Character[]::new)))
                .toArray(Character[][]::new);
    }

    @Override
    protected Long partOne(Character[][] input) {

        long times = 0;

        for (int i = 0; i < input.length; i++) {
            final Character[] characters = input[i];
            for (int j = 0; j < characters.length; j++) {
                final Character character = characters[j];
                if (character == 'X') {
                    final long count = countXmas(input, i, j);
                    times += count;
                }
            }
        }

        return times;
    }

    private long countXmas(Character[][] layout, int i, int j) {

        return Stream.of(validateXmas(layout, i - 1, j - 1, -1, -1, 1),
                         validateXmas(layout, i - 1, j, -1, 0, 1),
                         validateXmas(layout, i - 1, j + 1, -1, 1, 1),
                         validateXmas(layout, i, j - 1, 0, -1, 1),
                         validateXmas(layout, i, j + 1, 0, 1, 1),
                         validateXmas(layout, i + 1, j - 1, 1, -1, 1),
                         validateXmas(layout, i + 1, j, 1, 0, 1),
                         validateXmas(layout, i + 1, j + 1, 1, 1, 1))
                .filter(found -> found)
                .count();
    }

    private boolean validateXmas(Character[][] layout, int i, int j, int dirI, int dirJ, int index) {

        if (index == XMAS.length()) {

            return true;
        }

        if (i < 0 || i == layout.length || j < 0 || j == layout[i].length) {

            return false;
        }

        final Character c = layout[i][j];

        if (c == XMAS.charAt(index)) {

            return validateXmas(layout, i + dirI, j + dirJ, dirI, dirJ, index + 1);
        }

        return false;
    }

    @Override
    protected Long partTwo(Character[][] input) {

        long times = 0;

        for (int i = 0; i < input.length; i++) {
            final Character[] characters = input[i];
            for (int j = 0; j < characters.length; j++) {
                final Character character = characters[j];
                if (character == 'A' && validateXmas(input, i, j)) {
                    times++;
                }
            }
        }

        return times;
    }

    private boolean validateXmas(Character[][] layout, int i, int j) {

        final Character c1 = getCharacter(layout, i - 1, j - 1);
        final Character c2 = getCharacter(layout, i - 1, j + 1);
        final Character c3 = getCharacter(layout, i + 1, j - 1);
        final Character c4 = getCharacter(layout, i + 1, j + 1);

        if (c1 == null || c2 == null || c3 == null || c4 == null) {
            return false;
        }

        final List<Character> validCharacters = List.of('M', 'S');

        return List.of(c1, c4).containsAll(validCharacters) &&
                List.of(c2, c3).containsAll(validCharacters);
    }

    private Character getCharacter(Character[][] layout, int i, int j) {

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
