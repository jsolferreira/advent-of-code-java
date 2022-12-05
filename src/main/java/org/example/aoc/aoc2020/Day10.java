package org.example.aoc.aoc2020;

import java.util.List;

class Day10 extends AoC2020Day<List<Long>> {

    @Override
    protected List<Long> parseInput(String strInput) {

        return strInput.lines()
                .map(Long::parseLong)
                .sorted()
                .toList();
    }

    @Override
    protected Long partOne(List<Long> input) {

        long prev = 0;
        long sum1 = 0;
        long sum3 = 1;

        for (Long aLong : input) {

            if (aLong - prev == 1) {
                sum1++;
            } else if (aLong - prev == 3) {
                sum3++;
            }

            prev = aLong;
        }

        return sum1 * sum3;
    }

    @Override
    protected Long partTwo(List<Long> input) {

        int g = numberOfConnections(0L, input.get(0), input.get(1), input.get(2));
        long n = 1;

        for (int i = 0; i < input.size() - 3; i++) {

            int f = numberOfConnections(input.get(i), input.get(i + 1), input.get(i + 2), input.get(i + 3));

            if (f == 1 && g != 0) {

                if (g <= 2) {
                    n = n * g;
                } else {
                    n = n * (g - 1);
                }
                g = 0;
            } else if (f != 1) {
                g += f;
            }
        }

        if (g <= 2) {
            n = n * g;
        } else {
            n = n * (g + 1);
        }

        return n;
    }

    private int numberOfConnections(long a, long b, long c, long d) {

        int connections = 0;

        if (b - a == 1 || b - a == 2 || b - a == 3) {

            connections++;
        }

        if (c - a == 1 || c - a == 2 || c - a == 3) {

            connections++;
        }

        if (d - a == 1 || d - a == 2 || d - a == 3) {

            connections++;
        }

        return connections;
    }

    @Override
    protected String getDay() {

        return "day10";
    }
}
