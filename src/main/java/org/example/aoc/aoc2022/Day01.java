package org.example.aoc.aoc2022;

import java.util.Arrays;
import java.util.List;

class Day01 extends AoC2022Day<List<List<Long>>> {

    @Override
    protected List<List<Long>> parseInput(String strInput) {

        return Arrays.stream(strInput.split(System.lineSeparator() + System.lineSeparator()))
                .map(group -> group.lines().map(Long::parseLong).toList())
                .toList();
    }

    @Override
    protected Long partOne(List<List<Long>> input) {

        return input.stream()
                .mapToLong(this::sumCalories)
                .max()
                .orElseThrow();
    }

    @Override
    protected Long partTwo(List<List<Long>> input) {

        return input.stream()
                .mapToLong(this::sumCalories)
                .sorted()
                .skip(input.size() - 3L)
                .sum();
    }

    private long sumCalories(List<Long> calories) {

        return calories.stream().reduce(0L, Long::sum);
    }

    @Override
    protected String getDay() {

        return "day01";
    }
}
