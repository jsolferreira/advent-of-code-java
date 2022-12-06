package org.example.aoc.aoc2021;

import java.util.List;
import java.util.stream.IntStream;

class Day01 extends AoC2021Day<List<Integer>> {

    @Override
    protected List<Integer> parseInput(String strInput) {

        return strInput.lines()
                .map(Integer::parseInt)
                .toList();
    }

    @Override
    protected Long partOne(List<Integer> input) {

        return IntStream.range(0, input.size() - 1)
                .filter(i -> input.get(i + 1) > input.get(i))
                .count();
    }

    @Override
    protected Long partTwo(List<Integer> input) {

        return IntStream.range(0, input.size() - 3)
                .filter(i -> input.get(i + 3) + input.get(i + 2) + input.get(i + 1) > input.get(i + 2) + input.get(i + 1) + input.get(i))
                .count();
    }

    @Override
    protected String getDay() {

        return "day01";
    }
}
