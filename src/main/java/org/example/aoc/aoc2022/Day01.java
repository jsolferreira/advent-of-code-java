package org.example.aoc.aoc2022;

import java.util.Arrays;
import java.util.List;

class Day01 extends AoC2022Day<List<List<Integer>>> {

    @Override
    protected List<List<Integer>> parseInput(String strInput) {

        return Arrays.stream(strInput.split(System.lineSeparator() + System.lineSeparator()))
                .map(group -> group.lines().map(Integer::parseInt).toList())
                .toList();
    }

    @Override
    protected long partOne(List<List<Integer>> input) {

        return input.stream()
                .mapToInt(calories -> calories.stream().reduce(0, Integer::sum))
                .max()
                .orElseThrow();
    }

    @Override
    protected long partTwo(List<List<Integer>> input) {

        return input.stream()
                .mapToInt(calories -> calories.stream().reduce(0, Integer::sum))
                .sorted()
                .skip(input.size() - 3L)
                .sum();
    }

    @Override
    protected String getDay() {

        return "day01";
    }
}
