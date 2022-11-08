package org.example.aoc2020;

import java.util.List;
import java.util.stream.IntStream;

class Day01 extends AoC2020Day<List<Integer>> {

    @Override
    protected List<Integer> parseInput(String strInput) {

        return strInput.lines()
                .map(Integer::parseInt)
                .toList();
    }

    @Override
    protected long partOne(List<Integer> input) {

        return IntStream.range(0, input.size())
                .flatMap(i -> IntStream.range(i + 1, input.size())
                        .filter(j -> input.get(i) + input.get(j) == 2020)
                        .map(j -> input.get(i) * input.get(j)))
                .findFirst()
                .orElseThrow();
    }

    @Override
    protected long partTwo(List<Integer> input) {

        return IntStream.range(0, input.size())
                .flatMap(i -> IntStream.range(i + 1, input.size())
                        .filter(j -> input.get(i) + input.get(j) < 2020)
                        .flatMap(j -> IntStream.range(j + 1, input.size())
                                .filter(k -> input.get(i) + input.get(j) + input.get(k) == 2020)
                                .map(k -> input.get(i) * input.get(j) * input.get(k))))
                .findFirst()
                .orElseThrow();
    }

    @Override
    protected String getDay() {

        return "day01";
    }
}
