package org.example.aoc.aoc2022;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Day03 extends AoC2022Day<List<String>> {

    @Override
    protected List<String> parseInput(String strInput) {

        return strInput.lines().toList();
    }

    @Override
    protected long partOne(List<String> input) {

        return input.stream()
                .map(this::getItems)
                .flatMap(this::findCommonItemType)
                .mapToInt(this::getType)
                .sum();
    }

    @Override
    protected long partTwo(List<String> input) {

        return IntStream.iterate(0, i -> i < input.size(), i -> i + 3)
                .mapToObj(i -> input.subList(i, i + 3))
                .flatMap(this::findCommonItemType)
                .mapToInt(this::getType)
                .sum();
    }

    private List<String> getItems(String rucksack) {

        final int half = rucksack.length() / 2;
        return List.of(rucksack.substring(0, half), rucksack.substring(half));
    }

    private Stream<Character> findCommonItemType(List<String> items) {

        final String firstItem = items.get(0);
        final List<String> remainingItems = items.stream().skip(1).toList();

        return firstItem.chars()
                .filter(c1 -> remainingItems.stream().allMatch(item -> item.chars().anyMatch(c2 -> c2 == c1)))
                .mapToObj(c -> (char) c)
                .findFirst()
                .stream();
    }

    private int getType(char letter) {

        return Character.isUpperCase(letter) ? letter - 38 : letter - 96;
    }

    @Override
    protected String getDay() {

        return "day03";
    }
}
