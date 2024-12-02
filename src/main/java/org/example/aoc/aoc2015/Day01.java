package org.example.aoc.aoc2015;

import java.util.List;

class Day01 extends AoC2015Day<List<Character>> {

    @Override
    protected List<Character> parseInput(String strInput) {

        return strInput.chars().mapToObj(c -> (char) c).toList();
    }

    @Override
    protected Long partOne(List<Character> input) {

        return input.stream()
                .reduce(0L,
                        (acc, val) -> val == '(' ? acc + 1 : acc - 1,
                        (a, b) -> a);
    }

    @Override
    protected Long partTwo(List<Character> input) {

        int floor = 0;
        int i;

        for (i = 0; i < input.size() && floor != -1; i++) {

            floor += input.get(i) == '(' ? 1 : -1;
        }

        return (long) i;
    }

    @Override
    protected String getDay() {

        return "day01";
    }
}
