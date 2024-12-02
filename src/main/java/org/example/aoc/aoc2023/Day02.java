package org.example.aoc.aoc2023;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Day02 extends AoC2023Day<List<Day02.Game>> {

    private enum Color {
        RED,
        GREEN,
        BLUE
    }

    protected record Game(int id, List<Map<Color, Integer>> sets) {}

    private record MaxCounter(int reds, int greens, int blues) {}

    @Override
    protected List<Game> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> {

                    final String[] split = line.split(":");

                    final int gameId = Integer.parseInt(split[0].split(" ")[1]);

                    final List<Map<Color, Integer>> sets = Arrays.stream(split[1].split(";"))
                            .map(set -> Arrays.stream(set.trim().split(","))
                                    .map(subSet -> subSet.trim().split(" "))
                                    .collect(Collectors.toMap(subSet -> Color.valueOf(subSet[1].toUpperCase()),
                                                              subSet -> Integer.parseInt(subSet[0]))))
                            .toList();

                    return new Game(gameId, sets);
                })
                .toList();
    }

    @Override
    protected Integer partOne(List<Game> input) {

        return input.stream()
                .filter(game -> game.sets.stream()
                        .allMatch(subSet -> {

                            final int reds = subSet.getOrDefault(Color.RED, 0);
                            final int greens = subSet.getOrDefault(Color.GREEN, 0);
                            final int blues = subSet.getOrDefault(Color.BLUE, 0);

                            return reds <= 12 && greens <= 13 && blues <= 14;
                        }))
                .reduce(0, (acc, val) -> acc + val.id, Integer::sum);
    }

    @Override
    protected Integer partTwo(List<Game> input) {

        return input.stream()
                .map(game -> {

                    final MaxCounter initialCounter = new MaxCounter(0, 0, 0);

                    final MaxCounter maxCOunter = game.sets.stream().reduce(initialCounter, (acc, val) -> {
                        final int reds = val.getOrDefault(Color.RED, 0);
                        final int greens = val.getOrDefault(Color.GREEN, 0);
                        final int blues = val.getOrDefault(Color.BLUE, 0);

                        return new MaxCounter(Math.max(acc.reds, reds),
                                              Math.max(acc.greens, greens),
                                              Math.max(acc.blues, blues));
                    }, (a, b) -> new MaxCounter(0, 0, 0));

                    return maxCOunter.reds * maxCOunter.greens * maxCOunter.blues;
                })
                .reduce(0, Integer::sum);
    }

    @Override
    protected String getDay() {

        return "day02";
    }
}
