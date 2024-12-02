package org.example.aoc.aoc2020;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Day06 extends AoC2020Day<List<List<String>>> {

    @Override
    protected List<List<String>> parseInput(String strInput) {

        return Arrays.stream(strInput.split("\\r\\n\\r\\n"))
                .map(group -> Arrays.stream(group.split("\\r\\n")).toList())
                .toList();
    }

    @Override
    protected Long partOne(List<List<String>> input) {

        return input.stream().reduce(0L,
                                     (acc1, val1) -> acc1 + val1.stream()
                                             .reduce(new HashSet<Character>(),
                                                     (acc, val) -> {

                                                         final Set<Character> collect = stringToCharacterSet(val);
                                                         acc.addAll(collect);
                                                         return acc;
                                                     },
                                                     (a, b) -> new HashSet<>()).size(),
                                     Long::sum);
    }

    @Override
    protected Long partTwo(List<List<String>> input) {

        return input.stream().reduce(0L,
                                     (acc1, group) -> acc1 + group.stream()
                                             .reduce((Set<Character>) null,
                                                     (acc, val) -> {

                                                         final Set<Character> collect = stringToCharacterSet(val);

                                                         if (acc == null) {

                                                             return collect;
                                                         }

                                                         return acc.stream()
                                                                 .filter(collect::contains)
                                                                 .collect(Collectors.toUnmodifiableSet());
                                                     },
                                                     (a, b) -> new HashSet<>()).size(),
                                     Long::sum);
    }

    private Set<Character> stringToCharacterSet(String s) {

        return s.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    protected String getDay() {

        return "day06";
    }
}
