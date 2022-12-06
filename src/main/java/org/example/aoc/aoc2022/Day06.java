package org.example.aoc.aoc2022;

import java.util.stream.Collectors;
import java.util.stream.LongStream;

class Day06 extends AoC2022Day<String> {

    @Override
    protected String parseInput(String strInput) {

        return strInput;
    }

    @Override
    protected Long partOne(String input) {

        return LongStream.range(4, input.length())
                .filter(i -> isStartOfMarker(input, (int) i, 4))
                .findFirst()
                .orElseThrow();
    }

    @Override
    protected Long partTwo(String input) {

        return LongStream.range(14, input.length())
                .filter(i -> isStartOfMarker(input, (int) i, 14))
                .findFirst()
                .orElseThrow();
    }

    private boolean isStartOfMarker(String input, int i, int distinctCharacters) {

        return input.substring(i - distinctCharacters, i)
                .chars()
                .boxed()
                .collect(Collectors.toSet()).size() == distinctCharacters;
    }

    @Override
    protected String getDay() {

        return "day06";
    }
}
