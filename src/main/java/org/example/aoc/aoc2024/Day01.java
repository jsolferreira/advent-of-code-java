package org.example.aoc.aoc2024;

import org.example.aoc.utils.Pair;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day01 extends AoC2024Day<List<Pair<Integer, Integer>>> {

    @Override
    protected List<Pair<Integer, Integer>> parseInput(String strInput) {

        return strInput.lines()
                .map(l -> l.split("\\s+"))
                .map(s -> new Pair<>(Integer.parseInt(s[0]), Integer.parseInt(s[1])))
                .toList();
    }

    @Override
    protected Integer partOne(List<Pair<Integer, Integer>> input) {

        final List<Integer> first = input.stream().map(Pair::left).sorted().toList();
        final List<Integer> second = input.stream().map(Pair::right).sorted().toList();

        return IntStream.range(0, first.size())
                .reduce(0, (acc, i) -> {
                    Integer i1 = first.get(i);
                    Integer i2 = second.get(i);

                    return acc + Math.abs(i1 - i2);
                });
    }

    @Override
    protected Long partTwo(List<Pair<Integer, Integer>> input) {

        final Map<Integer, Long> countMap = input.stream()
                .map(Pair::right)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return input.stream().map(Pair::left)
                .map(i -> i * countMap.getOrDefault(i, 0L))
                .reduce(0L, Long::sum);
    }

    @Override
    protected String getDay() {

        return "day01";
    }
}
