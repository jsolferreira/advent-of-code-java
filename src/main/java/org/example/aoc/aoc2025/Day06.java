package org.example.aoc.aoc2025;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.LongBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day06 extends AoC2025Day<List<Day06.Problem>> {

    protected record Problem(char[][] numbers, LongBinaryOperator op) {}

    @Override
    protected List<Problem> parseInput(String strInput) {

        final String[] split = strInput.split(System.lineSeparator());

        final String firstLine = split[0];
        int from = 0;

        final List<Problem> problems = new ArrayList<>();

        for (int i = 0; i < firstLine.length(); i++) {
            final char c = firstLine.charAt(i);

            if (c == ' ') {
                int finalI = i;
                boolean isEndOfProblem = Arrays.stream(split).allMatch(s -> s.charAt(finalI) == ' ');

                if (isEndOfProblem) {
                    final Problem problem = extractProblem(split, from, i);
                    problems.add(problem);
                    from = i + 1;
                }
            }
        }
        final Problem problem = extractProblem(split, from, firstLine.length());
        problems.add(problem);

        return problems;
    }

    @Override
    protected Long partOne(List<Problem> input) {

        long sum = 0;

        for (Problem problem : input) {

            final char[][] numbers = problem.numbers();

            sum += Arrays.stream(numbers).mapToLong(n -> Long.parseLong(String.valueOf(n).trim()))
                    .reduce(problem.op())
                    .orElse(0);
        }

        return sum;
    }

    @Override
    protected Long partTwo(List<Problem> input) {

        long sum = 0;

        for (Problem problem : input) {

            final char[][] numbers = problem.numbers();

            sum += IntStream.range(0, numbers[0].length)
                    .mapToObj(ii -> Arrays.stream(Arrays.stream(numbers).map(chars -> chars[ii]).toArray())
                            .map(String::valueOf)
                            .collect(Collectors.joining()))
                    .map(s -> Long.parseLong(s.trim()))
                    .reduce(problem.op::applyAsLong)
                    .orElse(0L);
        }

        return sum;
    }

    private Problem extractProblem(String[] input, int from, int to) {

        final char op = input[input.length - 1].charAt(from);

        final char[][] numbers = IntStream.range(0, input.length - 1)
                .mapToObj(i -> input[i].substring(from, to).toCharArray())
                .toArray(char[][]::new);

        return new Problem(numbers, getOperation(op));
    }

    private LongBinaryOperator getOperation(char op) {

        if (op == '+') {

            return Long::sum;
        } else {

            return (a, b) -> a * b;
        }
    }

    @Override
    protected String getDay() {

        return "day06";
    }
}
