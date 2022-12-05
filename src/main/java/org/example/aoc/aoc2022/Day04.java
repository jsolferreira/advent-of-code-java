package org.example.aoc.aoc2022;

import java.util.Arrays;
import java.util.List;

class Day04 extends AoC2022Day<List<List<Day04.Pair>>> {

    protected record Pair(int sectionStart, int sectionEnd) {
    }

    @Override
    protected List<List<Pair>> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> Arrays.stream(line.split(","))
                        .map(pair -> pair.split("-"))
                        .map(split -> new Pair(Integer.parseInt(split[0]), Integer.parseInt(split[1])))
                        .toList())
                .toList();
    }

    @Override
    protected Long partOne(List<List<Pair>> input) {

        return input.stream()
                .filter(this::pairsFullyOverlap)
                .count();
    }

    private boolean pairsFullyOverlap(List<Pair> pairs) {

        final Pair pair1 = pairs.get(0);
        final Pair pair2 = pairs.get(1);

        return (pair1.sectionStart >= pair2.sectionStart && pair1.sectionEnd <= pair2.sectionEnd) ||
                (pair2.sectionStart >= pair1.sectionStart && pair2.sectionEnd <= pair1.sectionEnd);
    }

    @Override
    protected Long partTwo(List<List<Day04.Pair>> input) {

        return input.stream()
                .filter(this::pairsOverlap)
                .count();
    }

    private boolean pairsOverlap(List<Pair> pairs) {

        final Pair pair1 = pairs.get(0);
        final Pair pair2 = pairs.get(1);

        return pair1.sectionStart <= pair2.sectionEnd && pair1.sectionEnd >= pair2.sectionStart;
    }

    @Override
    protected String getDay() {

        return "day04";
    }
}
