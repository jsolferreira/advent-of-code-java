package org.example.aoc.aoc2021;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day03 extends AoC2021Day<List<List<Integer>>> {

    @Override
    protected List<List<Integer>> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> line.chars()
                        .mapToObj(Character::getNumericValue)
                        .toList())
                .toList();
    }

    @Override
    protected Long partOne(List<List<Integer>> input) {

        final int n = input.get(0).size();

        List<Map<Integer, Long>> mapStream = IntStream.range(0, n)
                .mapToObj(i -> input.stream()
                        .map(in -> in.get(i))
                        .collect((Collectors.groupingBy(Function.identity(),
                                                        Collectors.counting()))))
                .toList();

        String x = mapStream.stream()
                .flatMap(entry -> entry.entrySet().stream().max(Comparator.comparingInt(a -> a.getValue().intValue())).stream())
                .map(entry -> entry.getKey().toString())
                .collect(Collectors.joining());

        String y = mapStream.stream()
                .flatMap(entry -> entry.entrySet().stream().min(Comparator.comparingInt(a -> a.getValue().intValue())).stream())
                .map(entry -> entry.getKey().toString())
                .collect(Collectors.joining());

        return Long.parseLong(x, 2) * Long.parseLong(y, 2);
    }

    @Override
    protected Long partTwo(List<List<Integer>> input) {

        return null;
    }

    @Override
    protected String getDay() {

        return "day03";
    }
}
