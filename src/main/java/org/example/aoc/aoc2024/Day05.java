package org.example.aoc.aoc2024;

import org.example.aoc.utils.Pair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

class Day05 extends AoC2024Day<Pair<Map<Integer, Set<Integer>>, List<Integer[]>>> {

    @Override
    protected Pair<Map<Integer, Set<Integer>>, List<Integer[]>> parseInput(String strInput) {

        final String[] split = strInput.split(System.lineSeparator() + System.lineSeparator());

        final Map<Integer, Set<Integer>> collect = split[0].lines()
                .map(l -> l.split("\\|"))
                .collect(Collectors.groupingBy(x -> Integer.parseInt(x[0]),
                                               Collector.of(HashSet::new,
                                                            (r, x) -> r.add(Integer.parseInt(x[1])),
                                                            (r1, r2) -> {
                                                                r1.addAll(r2);
                                                                return r1;
                                                            })));

        final List<Integer[]> list = split[1].lines()
                .map(l -> Arrays.stream(l.split(","))
                        .map(Integer::parseInt)
                        .toArray(Integer[]::new))
                .toList();

        return new Pair<>(collect, list);
    }

    @Override
    protected Integer partOne(Pair<Map<Integer, Set<Integer>>, List<Integer[]>> input) {

        final Map<Integer, Set<Integer>> rules = input.left();
        final List<Integer[]> updates = input.right();

        int sum = 0;

        for (Integer[] update : updates) {
            boolean valid = true;
            int middleIndex = (int) Math.floor((double) update.length / 2);
            int middleValue = 1;

            for (int i = 1; i < update.length; i++) {
                final Integer previous = update[i - 1];
                final Integer current = update[i];

                final Set<Integer> beforeRule = rules.getOrDefault(current, Set.of());

                if (beforeRule.contains(previous)) {
                    valid = false;
                    break;
                }

                if (i == middleIndex) {
                    middleValue = current;
                }
            }

            if (valid) {
                sum += middleValue;
            }
        }

        return sum;
    }

    @Override
    protected Integer partTwo(Pair<Map<Integer, Set<Integer>>, List<Integer[]>> input) {

        final Map<Integer, Set<Integer>> rules = input.left();
        final List<Integer[]> updates = input.right();

        int sum = 0;

        for (Integer[] update : updates) {
            int middleIndex = (int) Math.floor((double) update.length / 2);

            final List<Integer> list = Arrays.stream(update)
                    .sorted((a, b) -> {

                        if (Objects.equals(a, b)) {
                            return 0;
                        }

                        final Set<Integer> beforeRule = rules.getOrDefault(a, Set.of());

                        if (beforeRule.contains(b)) {
                            return -1;
                        } else {
                            return 1;
                        }
                    })
                    .toList();

            if (!areSame(list, update)) {
                sum += list.get(middleIndex);
            }
        }

        return sum;
    }

    private boolean areSame(List<Integer> l1, Integer[] l2) {

        for (int i = 0; i < l1.size(); i++) {
            if (!l1.get(i).equals(l2[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected String getDay() {

        return "day05";
    }
}
