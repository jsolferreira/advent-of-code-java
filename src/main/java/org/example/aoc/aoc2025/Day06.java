package org.example.aoc.aoc2025;

import org.example.aoc.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.LongBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day06 extends AoC2025Day<Pair<char[][][], Character[]>> {

    @Override
    protected Pair<char[][][], Character[]> parseInput(String strInput) {

        final String[] split = strInput.split(System.lineSeparator());

        final String operationLine = split[split.length - 1];

        final Character[] operations = Arrays.stream(operationLine.split("\\s+"))
                .map(s -> s.charAt(0))
                .toArray(Character[]::new);

        final char[] operationChars = operationLine.toCharArray();
        int nSpaces = 0;

        int pos = 0;

        List<List<String>> xxx = new ArrayList<>();

        for (int i = 1; i <= operationChars.length; i++) {
            if (i == operationChars.length || operationChars[i] != ' ') {

                final List<String> chunks = new ArrayList<>();

                for (int ii = 0; ii < split.length - 1; ii++) {
                    final String row = split[ii];

                    final String chunk = nSpaces == 0
                            ? row.substring(pos)
                            : row.substring(pos, pos + nSpaces);

                    chunks.add(chunk);
                }

                xxx.add(chunks);
                pos += nSpaces + 1;
                nSpaces = 0;
            } else {

                nSpaces++;
            }
        }

        final char[][][] c = xxx.stream()
                .map(xx -> xx.stream()
                        .map(String::toCharArray)
                        .toArray(char[][]::new))
                .toArray(char[][][]::new);

        return new Pair<>(c, operations);
    }

    @Override
    protected Long partOne(Pair<char[][][], Character[]> input) {

        final char[][][] numbers = input.left();
        final Character[] operations = input.right();

        long sum = 0;

        for (int i = 0; i < numbers.length; i++) {
            final LongBinaryOperator op = getOperation(operations[i]);

            sum += Arrays.stream(numbers[i]).mapToLong(n -> Long.parseLong(String.valueOf(n).trim()))
                    .reduce(op)
                    .orElse(0);
        }

        return sum;
    }

    @Override
    protected Long partTwo(Pair<char[][][], Character[]> input) {

        final char[][][] numbers = input.left();
        final Character[] operations = input.right();

        long sum = 0;

        for (int i = 0; i < numbers.length; i++) {
            final LongBinaryOperator op = getOperation(operations[i]);

            final char[][] number = numbers[i];

            sum += IntStream.range(0, number[0].length)
                    .mapToObj(ii -> Arrays.stream(Arrays.stream(number).map(chars -> chars[ii]).toArray())
                            .map(String::valueOf)
                            .collect(Collectors.joining()))
                    .map(s -> Long.parseLong(s.trim()))
                    .reduce(op::applyAsLong)
                    .orElse(0L);
        }

        return sum;
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
