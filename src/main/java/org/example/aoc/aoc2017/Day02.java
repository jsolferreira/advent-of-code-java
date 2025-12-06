package org.example.aoc.aoc2017;

import java.util.Arrays;
import java.util.List;

class Day02 extends AoC2017Day<List<List<Long>>> {

    @Override
    protected List<List<Long>> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> Arrays.stream(line.split("\\s+"))
                        .map(Long::parseLong)
                        .toList())
                .toList();
    }

    @Override
    protected Long partOne(List<List<Long>> input) {

        long sum = 0;

        for (List<Long> row : input) {

            long max = Long.MIN_VALUE;
            long min = Long.MAX_VALUE;

            for (Long n : row) {
                if (n > max) {
                    max = n;
                }
                if (n < min) {
                    min = n;
                }
            }

            sum += max - min;
        }

        return sum;
    }

    @Override
    protected Long partTwo(List<List<Long>> input) {

        long sum = 0;

        for (List<Long> row : input) {

            for (int i = 0; i < row.size() - 1; i++) {
                for (int j = i + 1; j < row.size(); j++) {
                    if (row.get(i) % row.get(j) == 0) {
                        sum += row.get(i) / row.get(j);
                    } else if (row.get(j) % row.get(i) == 0) {
                        sum += row.get(j) / row.get(i);
                    }
                }
            }
        }

        return sum;
    }

    @Override
    protected String getDay() {

        return "day02";
    }
}
