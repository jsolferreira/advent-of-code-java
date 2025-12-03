package org.example.aoc.aoc2025;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Day03 extends AoC2025Day<List<String>> {

    @Override
    protected List<String> parseInput(String strInput) {

        return strInput.lines().toList();
    }

    @Override
    protected Long partOne(List<String> input) {

        return input.stream()
                .map(s -> findJoltage(s, 2))
                .reduce(Long::sum)
                .orElse(0L);
    }

    @Override
    protected Long partTwo(List<String> input) {

        return input.stream()
                .map(s -> findJoltage(s, 12))
                .reduce(Long::sum)
                .orElse(0L);
    }

    private long findJoltage(String bank, int nBatteries) {

        final Character[] chars = bank.substring(0, nBatteries).chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        final int lastIndex = nBatteries - 1;

        for (int i = nBatteries; i < bank.length(); i++) {

            char c = bank.charAt(i);
            int j = 0;

            for (; j < chars.length - 1; j++) {
                char c1 = chars[j];
                char c2 = chars[j + 1];

                if (c2 > c1) {
                    shiftLeft(chars, j);
                    chars[lastIndex] = c;
                    break;
                }
            }

            if (chars[lastIndex] < c) {
                chars[lastIndex] = c;
            }
        }

        final String collect = Arrays.stream(chars).map(String::valueOf).collect(Collectors.joining());

        return Long.parseLong(collect);
    }

    private void shiftLeft(Character[] chars, int index) {

        for (int i = index; i < chars.length - 1; i++) {
            chars[i] = chars[i + 1];
        }
    }

    @Override
    protected String getDay() {

        return "day03";
    }
}
