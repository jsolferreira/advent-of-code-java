package org.example.aoc.aoc2022;

import java.util.Arrays;
import java.util.stream.IntStream;

class Day08 extends AoC2022Day<Integer[][]> {

    @Override
    protected Integer[][] parseInput(String strInput) {

        return strInput.lines()
                .map(line -> Arrays.stream(line.split(""))
                        .map(Integer::parseInt)
                        .toArray(Integer[]::new))
                .toArray(Integer[][]::new);
    }

    @Override
    protected Long partOne(Integer[][] input) {

        return IntStream.range(0, input.length)
                .flatMap(i -> IntStream.range(0, input[i].length).filter(j -> isVisible(input, i, j)))
                .count();
    }

    private boolean isVisible(Integer[][] input, int i, int j) {

        return IntStream.range(0, i).allMatch(ii -> input[ii][j] < input[i][j])
                || IntStream.range(i + 1, input.length).allMatch(ii -> input[ii][j] < input[i][j])
                || IntStream.range(0, j).allMatch(jj -> input[i][jj] < input[i][j])
                || IntStream.range(j + 1, input[i].length).allMatch(jj -> input[i][jj] < input[i][j]);
    }

    @Override
    protected Long partTwo(Integer[][] input) {

        return IntStream.range(1, input.length - 1)
                .boxed()
                .flatMapToLong(i -> IntStream.range(1, input[i].length - 1)
                        .mapToLong(j -> calculateScenicScore(input, i, j)))
                .max()
                .orElseThrow();
    }

    private long calculateScenicScore(Integer[][] input, int i, int j) {

        final long up = IntStream.range(0, i)
                .map(ii -> i - ii - 1)
                .filter(ii -> input[ii][j] >= input[i][j])
                .map(ii -> i - ii)
                .findFirst()
                .orElse(i);

        final long down = IntStream.range(i + 1, input.length)
                .filter(ii -> input[ii][j] >= input[i][j])
                .map(ii -> ii - i)
                .findFirst()
                .orElse(input.length - 1 - i);

        final long left = IntStream.range(0, j)
                .map(jj -> j - jj - 1)
                .filter(jj -> input[i][jj] >= input[i][j])
                .map(jj -> j - jj)
                .findFirst()
                .orElse(j);

        final long right = IntStream.range(j + 1, input[i].length)
                .filter(jj -> input[i][jj] >= input[i][j])
                .map(jj -> jj - j)
                .findFirst()
                .orElse(input[i].length - 1 - j);

        return up * down * left * right;
    }

    @Override
    protected String getDay() {

        return "day08";
    }
}
