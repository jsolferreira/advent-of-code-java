package org.example.aoc.aoc2025;

import org.example.aoc.utils.Pair;

import java.util.Arrays;
import java.util.List;

class Day02 extends AoC2025Day<List<Pair<Long, Long>>> {

    @Override
    protected List<Pair<Long, Long>> parseInput(String strInput) {

        return Arrays.stream(strInput.split(","))
                .map(s -> s.split("-"))
                .map(s -> new Pair<>(Long.parseLong(s[0]), Long.parseLong(s[1])))
                .toList();
    }

    @Override
    protected Long partOne(List<Pair<Long, Long>> input) {

        long sum = 0;

        for (Pair<Long, Long> pair : input) {

            for (long i = pair.left(); i <= pair.right(); i++) {

                if (isInvalid(i)) {
                    sum += i;
                }
            }
        }

        return sum;
    }

    private boolean isInvalid(long n) {

        final String string = String.valueOf(n);

        return string.substring(0, string.length() / 2).equals(string.substring(string.length() / 2));
    }

    @Override
    protected Integer partTwo(List<Pair<Long, Long>> input) {

        return null;
    }

    @Override
    protected String getDay() {

        return "day02";
    }
}
