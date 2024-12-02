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

        return findFirstMarker(input, 4);
    }

    @Override
    protected Long partTwo(String input) {

        return findFirstMarker(input, 14);
    }

    private Long findFirstMarker(String input, int distinctCharacters) {

        return LongStream.range(distinctCharacters, input.length())
                .filter(i -> isStartOfMarker(input, (int) i, distinctCharacters))
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
