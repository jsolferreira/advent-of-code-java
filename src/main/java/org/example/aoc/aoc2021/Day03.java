package org.example.aoc.aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day03 extends AoC2021Day<List<List<String>>> {

    @Override
    protected List<List<String>> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> Arrays.asList(line.split("")))
                .toList();
    }

    @Override
    protected Long partOne(List<List<String>> input) {

        final int n = input.get(0).size();

        final List<Map<String, Long>> bitCounting = IntStream.range(0, n)
                .mapToObj(i -> input.stream()
                        .map(in -> in.get(i))
                        .collect((Collectors.groupingBy(Function.identity(),
                                                        Collectors.counting()))))
                .toList();

        final String gammaRate = bitCounting.stream()
                .flatMap(entry -> entry.entrySet().stream()
                        .max(Comparator.comparingLong(Map.Entry::getValue))
                        .map(Map.Entry::getKey)
                        .stream())
                .collect(Collectors.joining());

        final String epsilon = bitCounting.stream()
                .flatMap(entry -> entry.entrySet().stream()
                        .min(Comparator.comparingLong(Map.Entry::getValue))
                        .map(Map.Entry::getKey)
                        .stream())
                .collect(Collectors.joining());

        return Long.parseLong(gammaRate, 2) * Long.parseLong(epsilon, 2);
    }

    @Override
    protected Long partTwo(List<List<String>> input) {

        return null;
    }

    @Override
    protected String getDay() {

        return "day03";
    }
}
