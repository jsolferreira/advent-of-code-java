package org.example.aoc.aoc2021;

import java.util.Arrays;
import java.util.Collection;
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

        final int n = input.getFirst().size();

        final List<Map<String, Long>> bitCounting = IntStream.range(0, n)
                .mapToObj(i -> countBitsInPosition(input, i))
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

        final int n = input.getFirst().size();

        final String oxygenGeneratorRating = IntStream.range(0, n)
                .boxed()
                .reduce(input,
                        (acc, val) -> {
                            final Map<String, Long> bitCounting = countBitsInPosition(acc, val);

                            return bitCounting.entrySet().stream()
                                    .max(Comparator.comparingLong(Map.Entry<String, Long>::getValue).thenComparing(Map.Entry::getKey))
                                    .map(Map.Entry::getKey)
                                    .stream()
                                    .flatMap(bit -> acc.stream().filter(bits -> bits.get(val).equals(bit)))
                                    .toList();
                        },
                        (a, _) -> a)
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.joining());

        final String co2ScrubberRating = IntStream.range(0, n)
                .boxed()
                .reduce(input,
                        (acc, val) -> {
                            final Map<String, Long> bitCounting = countBitsInPosition(acc, val);

                            return bitCounting.entrySet().stream()
                                    .min(Comparator.comparingLong(Map.Entry<String, Long>::getValue).thenComparing(Map.Entry::getKey))
                                    .map(Map.Entry::getKey)
                                    .stream()
                                    .flatMap(bit -> acc.stream().filter(bits -> bits.get(val).equals(bit)))
                                    .toList();
                        },
                        (a, _) -> a)
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.joining());

        return Long.parseLong(oxygenGeneratorRating, 2) * Long.parseLong(co2ScrubberRating, 2);
    }

    private Map<String, Long> countBitsInPosition(List<List<String>> bits, int position) {

        return bits.stream()
                .map(in -> in.get(position))
                .collect(Collectors.groupingBy(Function.identity(),
                                               Collectors.counting()));
    }

    @Override
    protected String getDay() {

        return "day03";
    }
}
