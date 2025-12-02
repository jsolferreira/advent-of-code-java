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
    protected Long partTwo(List<Pair<Long, Long>> input) {

        long sum = 0;

        for (Pair<Long, Long> pair : input) {

            for (long i = pair.left(); i <= pair.right(); i++) {

                if (isInvalidPart2(i)) {
                    sum += i;
                }
            }
        }

        return sum;
    }

    private boolean isInvalidPart2(long n) {

        final String string = String.valueOf(n);

        for (int i = 1; i < string.length(); i++) {

            if (validateChunks(string, i)) {
                return true;
            }
        }

        return false;
    }

    private boolean validateChunks(String s, int chunkSize) {

        if (chunkSize >= s.length()) {
            return false;
        }

        int i = 0;
        String previousChunk = null;

        for (; i + chunkSize < s.length(); i += chunkSize) {

            final String chunk = s.substring(i, i + chunkSize);

            if (previousChunk == null) {

                previousChunk = chunk;
            } else {

                if (!previousChunk.equals(chunk)) {
                    return false;
                }
            }
        }

        return previousChunk.equals(s.substring(i));
    }

    @Override
    protected String getDay() {

        return "day02";
    }
}
