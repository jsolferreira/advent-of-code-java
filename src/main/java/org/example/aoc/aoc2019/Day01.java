package org.example.aoc.aoc2019;

import java.util.List;
import java.util.stream.LongStream;

class Day01 extends AoC2019Day<List<Long>> {

    @Override
    protected List<Long> parseInput(String strInput) {

        return strInput.lines()
                .map(Long::parseLong)
                .toList();
    }

    @Override
    protected Long partOne(List<Long> input) {

        return input.stream()
                .mapToLong(this::calculateFuel)
                .sum();
    }

    @Override
    protected Long partTwo(List<Long> input) {

        return input.stream()
                .flatMapToLong(mass -> LongStream.iterate(mass, m -> m > 0, this::calculateFuel).skip(1))
                .sum();
    }

    private long calculateFuel(long mass) {

        return mass / 3 - 2;
    }

    @Override
    protected String getDay() {

        return "day01";
    }
}
