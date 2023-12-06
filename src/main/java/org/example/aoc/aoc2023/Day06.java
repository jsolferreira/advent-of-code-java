package org.example.aoc.aoc2023;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day06 extends AoC2023Day<List<Day06.Race>> {

    protected record Race(int time, int distance) {}

    @Override
    protected List<Race> parseInput(String strInput) {

        final String[] split = strInput.split("\n");

        final Integer[] times = Arrays
                .stream(split[0].split("\\s+"))
                .skip(1)
                .map(String::trim)
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
        final Integer[] distances = Arrays
                .stream(split[1].split("\\s+"))
                .skip(1)
                .map(String::trim)
                .map(Integer::parseInt)
                .toArray(Integer[]::new);

        return IntStream.range(0, times.length)
                .mapToObj(i -> new Race(times[i], distances[i]))
                .toList();
    }

    @Override
    protected Long partOne(List<Race> input) {

        return input.stream()
                .reduce(1L, (acc, val) -> acc * resolveRecord(val.time, val.distance), Long::sum);
    }

    @Override
    protected Long partTwo(List<Race> input) {

        long time = Long.parseLong(input.stream().map(Race::time).map(String::valueOf).collect(Collectors.joining()));
        long distance = Long.parseLong(input.stream().map(Race::distance).map(String::valueOf).collect(Collectors.joining()));

        return resolveRecord(time, distance);
    }

    private long resolveRecord(long time, long distance) {

        final double min = quadratic(-1, time, -distance, 1);
        final double max = quadratic(-1, time, -distance, -1);

        return resolveDifference(min, max);
    }

    private double quadratic(long a, long b, long c, long sign) {

        return (-b + sign * Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
    }

    private long resolveDifference(double min, double max) {

        return (long) ((Math.floor(max) - Math.floor(min)) - (min == Math.floor(min) ? 1 : 0) - (max == Math.floor(max) ? 1 : 0));
    }

    @Override
    protected String getDay() {

        return "day06";
    }
}
