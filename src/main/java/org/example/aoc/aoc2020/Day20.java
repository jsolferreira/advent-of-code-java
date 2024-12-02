package org.example.aoc.aoc2020;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day20 extends AoC2020Day<List<Day20.Tile>> {

    protected record Tile(List<String> sides, int number) {
    }

    @Override
    protected List<Day20.Tile> parseInput(String strInput) {

        return Arrays.stream(strInput.split(System.lineSeparator() + System.lineSeparator()))
                .map(t -> {
                    final String[] split = t.split(System.lineSeparator());
                    final int number = Integer.parseInt(split[0].replaceAll("[^0-9]", ""));

                    final List<String> lines = Arrays.stream(split).skip(1).toList();

                    final String side1 = lines.get(0);
                    final String side1Reversed = new StringBuilder(side1).reverse().toString();
                    final String side2 = lines.stream().map(line -> line.charAt(0)).map(Object::toString).collect(Collectors.joining());
                    final String side2Reversed = new StringBuilder(side2).reverse().toString();
                    final String side3 = lines
                            .stream()
                            .map(line -> line.charAt(line.length() - 1))
                            .map(Object::toString)
                            .collect(Collectors.joining());
                    final String side3Reversed = new StringBuilder(side3).reverse().toString();
                    final String side4 = lines.get(lines.size() - 1);
                    final String side4Reversed = new StringBuilder(side4).reverse().toString();

                    return new Tile(List.of(side1, side1Reversed, side2, side2Reversed, side3, side3Reversed, side4, side4Reversed),
                                    number);
                })
                .collect(Collectors.toList());
    }

    @Override
    protected Long partOne(List<Day20.Tile> input) {

        Map<Integer, List<Tile>> collect = input.stream()
                .map(tile -> {
                    List<Tile> collect1 = input.stream()
                            .filter(tile1 -> tile.number != tile1.number)
                            .filter(tile1 -> {

                                Optional<String> s = tile.sides
                                        .stream()
                                        .filter(side -> tile1.sides.stream().anyMatch(side::equals))
                                        .findFirst();

                                return tile.sides
                                        .stream()
                                        .anyMatch(side -> tile1.sides.stream().anyMatch(side::equals));
                            })
                            .collect(Collectors.toList());

                    return Map.entry(tile.number, collect1);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return input.stream()
                .filter(tile -> input.stream()
                        .filter(tile1 -> tile.number != tile1.number)
                        .flatMap(tile1 -> tile.sides.stream().filter(side -> tile1.sides.stream().anyMatch(side::equals)))
                        .count() == 4)
                .reduce(1L, (acc, val) -> acc * val.number, (a, b) -> a * b);
    }

    @Override
    protected Long partTwo(List<Day20.Tile> input) {

        return null;
    }

    @Override
    protected String getDay() {

        return "day20";
    }
}
