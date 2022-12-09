package org.example.aoc.aoc2020;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

class Day09 extends AoC2020Day<List<Long>> {

    @Override
    protected List<Long> parseInput(String strInput) {

        return strInput.lines()
                .map(Long::parseLong)
                .toList();
    }

    @Override
    protected Long partOne(List<Long> input) {

        return IntStream.range(25, input.size())
                .filter(i -> !validateNumber(input, i, input.get(i)))
                .mapToObj(input::get)
                .findFirst()
                .orElseThrow();
    }

    private boolean validateNumber(List<Long> input, int index, long n) {

        return IntStream.range(index - 25, index)
                .anyMatch(i -> IntStream.range(i, index)
                        .anyMatch(j -> input.get(i) + input.get(j) == n));
    }

    @Override
    protected Long partTwo(List<Long> input) {

        final LinkedList<Long> contiguous = new LinkedList<>();
        Long sum = 0L;

        for (int i = 1; i < input.size(); i++) {
            final long a = input.get(i);

            if (a == (long) partOneResult) {
                throw new RuntimeException();
            }

            if (sum + a > (long) partOneResult) {
                final Long removed = contiguous.pop();
                sum -= removed;
                i--;
            } else if (sum + a < (long) partOneResult) {
                contiguous.add(a);
                sum += a;
            } else {
                return Collections.min(contiguous) + Collections.max(contiguous);
            }
        }

        throw new RuntimeException();
    }

    @Override
    protected String getDay() {

        return "day09";
    }
}
