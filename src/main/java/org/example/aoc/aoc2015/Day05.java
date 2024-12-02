package org.example.aoc.aoc2015;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

class Day05 extends AoC2015Day<List<String>> {

    @Override
    protected List<String> parseInput(String strInput) {

        return strInput.lines().toList();
    }

    @Override
    protected Long partOne(List<String> input) {

        final Set<String> blacklistedStrings = Set.of("ab", "cd", "pq", "xy");
        final Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u');

        return input.stream()
                .filter(string -> {

                    final List<String> strings = IntStream.range(0, string.length() - 1)
                            .mapToObj(i -> string.substring(i, i + 2))
                            .toList();

                    long nVowels = string.chars().filter(c -> vowels.contains((char) c)).count();
                    boolean oneLetterAppearsTwiceInARow = false;
                    boolean containBlacklistedStrings = false;

                    for (String s : strings) {

                        if (!oneLetterAppearsTwiceInARow) {

                            oneLetterAppearsTwiceInARow = s.charAt(0) == s.charAt(1);
                        }

                        if (!containBlacklistedStrings) {

                            containBlacklistedStrings = blacklistedStrings.contains(s);
                        }
                    }

                    return nVowels >= 3 && oneLetterAppearsTwiceInARow && !containBlacklistedStrings;
                })
                .count();
    }

    @Override
    protected Long partTwo(List<String> input) {

        return input.stream()
                .filter(string -> {

                    long nVowels = 0;
                    boolean oneLetterRepeats = false;

                    for (int i = 0; i < string.length(); i++) {

                        /*if (isVowel(string.charAt(i))) {

                            nVowels++;
                        }*/

                    }

                    final List<String> strings = IntStream.range(0, string.length() - 1)
                            .mapToObj(i -> string.substring(i, i + 2))
                            .toList();

                    boolean oneLetterAppearsTwiceInARow = false;
                    boolean containBlacklistedStrings = false;

                    for (String s : strings) {

                        if (!oneLetterAppearsTwiceInARow) {

                            oneLetterAppearsTwiceInARow = s.charAt(0) == s.charAt(1);
                        }

                    }

                    return nVowels >= 3 && oneLetterAppearsTwiceInARow && !containBlacklistedStrings;
                })
                .count();
    }

    @Override
    protected String getDay() {

        return "day05";
    }
}
