package org.example.aoc.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

class Day09 extends AoC2023Day<List<List<Integer>>> {

    @Override
    protected List<List<Integer>> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> Arrays.stream(line.split("\\s+"))
                        .map(Integer::parseInt)
                        .toList())
                .toList();
    }

    @Override
    protected Integer partOne(List<List<Integer>> input) {

        return resolve(input, (list, acc) -> list.getLast() + acc);
    }

    @Override
    protected Integer partTwo(List<List<Integer>> input) {

        return resolve(input, (list, acc) -> list.getFirst() - acc);
    }

    private Integer resolve(List<List<Integer>> input, BiFunction<List<Integer>, Integer, Integer> f) {

        return input.stream()
                .map(this::findExtrapolations)
                .reduce(0,
                        (acc, val) -> acc + findNextValue(val, f),
                        Integer::sum);
    }

    private List<List<Integer>> findExtrapolations(List<Integer> valueHistory) {

        final List<List<Integer>> extrapolations = new ArrayList<>(List.of(valueHistory));

        List<Integer> currentExtrapolation = valueHistory;
        boolean keepLooping = true;

        while (keepLooping) {
            final List<Integer> nextExtrapolation = new ArrayList<>();

            boolean allZero = true;

            for (int i = 1; i < currentExtrapolation.size(); i++) {
                final int nextExtrapolationValue = currentExtrapolation.get(i) - currentExtrapolation.get(i - 1);
                allZero = allZero && nextExtrapolationValue == 0;
                nextExtrapolation.add(nextExtrapolationValue);
            }

            extrapolations.add(nextExtrapolation);
            currentExtrapolation = nextExtrapolation;
            keepLooping = !allZero;
        }

        return extrapolations;
    }

    private int findNextValue(List<List<Integer>> extrapolations, BiFunction<List<Integer>, Integer, Integer> f) {

        final int j = extrapolations.size() - 2;

        return IntStream.rangeClosed(0, j)
                .map(i -> j - i)
                .mapToObj(extrapolations::get)
                .reduce(0,
                        (acc, val) -> f.apply(val, acc),
                        Integer::sum);
    }

    @Override
    protected String getDay() {

        return "day09";
    }
}
