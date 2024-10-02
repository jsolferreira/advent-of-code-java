package org.example.aoc.aoc2023;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.stream.IntStream;

class Day13 extends AoC2023Day<List<Character[][]>> {

    @Override
    protected List<Character[][]> parseInput(String strInput) {

        return Arrays.stream(strInput.split(System.lineSeparator() + System.lineSeparator()))
                .map(s -> s.lines()
                        .map(ss -> ss.chars().mapToObj(c -> (char) c).toArray(Character[]::new))
                        .toArray(Character[][]::new))
                .toList();
    }

    @Override
    protected Integer partOne(List<Character[][]> input) {

        int s = 0;

        for (Character[][] chars : input) {
            Character[][] transpose = transpose(chars);

            int horizontal = x(chars);
            int vertical = x(transpose);

            s += horizontal * 100 + vertical;

            System.out.println();
        }

        // 68296 -> too high
        // 21159 -> too low
        return s;
    }

    private int x(Character[][] input) {

        Character[] previous = null;
        Deque<Character[]> pile = new ArrayDeque<>();
        boolean equalsFound = false;
        int s = 0;

        for (Character[] x1 : input) {
            if (previous == null) {
                pile.push(x1);
                previous = x1;
                continue;
            }

            if (equalsFound) {
                if (pile.isEmpty()) {
                    equalsFound = false;
                    s = 0;
                } else {
                    Character[] pop = pile.pop();
                    if (Arrays.equals(pop, x1)) {
                        s++;
                    } else {
                        equalsFound = false;
                        s = 0;
                        pile.clear();
                    }
                }
            } else {
                equalsFound = Arrays.equals(previous, x1);
                if (!equalsFound) {
                    pile.push(x1);
                } else {
                    if (!pile.isEmpty()) {
                        pile.pop();
                        s++;
                    }
                }
            }

            previous = x1;
        }

        if (s == 0) {
            pile.clear();
        }

        return s + pile.size();
    }

    private Character[][] transpose(Character[][] input) {

        int rowSize = input[input.length - 1].length;

        return IntStream.range(0, rowSize)
                .mapToObj(j -> Arrays.stream(input).map(characters -> characters[j]).toArray(Character[]::new))
                .toArray(Character[][]::new);


    }

    @Override
    protected Object partTwo(List<Character[][]> input) {

        return null;
    }

    @Override
    protected String getDay() {

        return "day13";
    }
}
