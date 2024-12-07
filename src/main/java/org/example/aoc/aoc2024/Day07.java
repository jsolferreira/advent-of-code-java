package org.example.aoc.aoc2024;

import org.example.aoc.utils.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.function.LongBinaryOperator;

class Day07 extends AoC2024Day<List<Pair<Long, List<Long>>>> {

    @Override
    protected List<Pair<Long, List<Long>>> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> {
                    final String[] split = line.split(": ");

                    final List<Long> right = Arrays.stream(split[1].split(" ")).map(Long::parseLong).toList();

                    return new Pair<>(Long.parseLong(split[0]), right);
                })
                .toList();
    }

    @Override
    protected Long partOne(List<Pair<Long, List<Long>>> input) {

        return run(input, List.of(Long::sum,(a, b) -> a * b));
    }

    @Override
    protected Long partTwo(List<Pair<Long, List<Long>>> input) {

        return run(input, List.of(Long::sum,(a, b) -> a * b, this::concat));
    }

    private long run(List<Pair<Long, List<Long>>> input, List<LongBinaryOperator> funcs) {

        return input.stream()
                .filter(pair -> applyOperator(pair.left(), pair.right().getFirst(), pair.right(), 1, funcs))
                .mapToLong(Pair::left)
                .sum();
    }

    private boolean applyOperator(long testValue, long previousNumber, List<Long> numbers, int index, List<LongBinaryOperator> funcs) {

        if (index == numbers.size()) {
            return previousNumber == testValue;
        }

        final long currentNumber = numbers.get(index);

        return funcs
                .stream()
                .map(func -> func.applyAsLong(previousNumber, currentNumber))
                .anyMatch(nextNumber -> applyOperator(testValue, nextNumber, numbers, index + 1, funcs));
    }

    private Long concat(long a, long b) {

        return Long.parseLong(String.format("%d%d", a, b));
    }

    @Override
    protected String getDay() {

        return "day07";
    }
}
