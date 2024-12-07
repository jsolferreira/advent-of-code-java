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

        long total = 0;

        for (Pair<Long, List<Long>> pair : input) {
            Long testValue = pair.left();
            List<Long> numbers = pair.right();

            boolean p1 = applyOperator(testValue, numbers.getFirst(), numbers, 1, Long::sum);
            boolean p2 = applyOperator(testValue, numbers.getFirst(), numbers, 1, (a, b) -> a * b);

            if(p1 || p2) {
                total += testValue;
            }
        }

        return total;
    }

    private boolean applyOperator(long testValue, long previousNumber, List<Long> numbers, int index, LongBinaryOperator func) {

        final long currentNumber = numbers.get(index);

        final long nextNumber = func.applyAsLong(previousNumber, currentNumber);

        if (index + 1 == numbers.size()) {
            return nextNumber == testValue;
        }

        return applyOperator(testValue, nextNumber, numbers, index + 1, Long::sum) ||
                applyOperator(testValue, nextNumber, numbers, index + 1, (a, b) -> a * b);
    }

    @Override
    protected Long partTwo(List<Pair<Long, List<Long>>> input) {

        long total = 0;

        for (Pair<Long, List<Long>> pair : input) {
            Long testValue = pair.left();
            List<Long> numbers = pair.right();

            boolean p1 = test2(testValue, numbers.getFirst(), numbers, 1, Long::sum);
            boolean p2 = test2(testValue, numbers.getFirst(), numbers, 1, (a, b) -> a * b);
            boolean p3 = test2(testValue, numbers.getFirst(), numbers, 1, this::concat);

            if(p1 || p2 || p3) {
                total += testValue;
            }
        }

        return total;
    }

    private boolean test2(long testValue, long previousNumber, List<Long> numbers, int index, LongBinaryOperator func) {

        final long currentNumber = numbers.get(index);

        final long nextNumber = func.applyAsLong(previousNumber, currentNumber);

        if (index + 1 == numbers.size()) {
            return nextNumber == testValue;
        }

        return test2(testValue, nextNumber, numbers, index + 1, Long::sum) ||
                test2(testValue, nextNumber, numbers, index + 1, (a, b) -> a * b) ||
                test2(testValue, nextNumber, numbers, index + 1, this::concat);
    }

    private Long concat(long a, long b) {

        return Long.parseLong(String.format("%d%d", a, b));
    }

    @Override
    protected String getDay() {

        return "day07";
    }
}
