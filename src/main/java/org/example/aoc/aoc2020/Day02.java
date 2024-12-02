package org.example.aoc.aoc2020;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day02 extends AoC2020Day<List<Day02.PasswordValidation>> {

    protected record PasswordValidation(int lowerBound, int upperBound, char letter, String password) {
    }

    @Override
    protected List<PasswordValidation> parseInput(String strInput) {

        final Pattern pattern = Pattern.compile("(\\d+)-(\\d+) (\\w): (\\w+)");

        return strInput.lines()
                .map(pattern::matcher)
                .filter(Matcher::matches)
                .map(matcher -> new PasswordValidation(Integer.parseInt(matcher.group(1)),
                                                       Integer.parseInt(matcher.group(2)),
                                                       matcher.group(3).charAt(0),
                                                       matcher.group(4)))
                .toList();
    }

    @Override
    protected Long partOne(List<PasswordValidation> input) {

        return input.stream()
                .filter(passwordValidation -> {
                    final long count = passwordValidation.password.chars()
                            .filter(c -> c == passwordValidation.letter)
                            .count();

                    return count >= passwordValidation.lowerBound && count <= passwordValidation.upperBound;
                })
                .count();
    }

    @Override
    protected Long partTwo(List<PasswordValidation> input) {

        return input.stream()
                .filter(passwordValidation -> {

                    final char firstPosition = passwordValidation.password.charAt(passwordValidation.lowerBound - 1);
                    final char secondPosition = passwordValidation.password.charAt(passwordValidation.upperBound - 1);

                    return firstPosition != secondPosition &&
                            (firstPosition == passwordValidation.letter || secondPosition == passwordValidation.letter);
                })
                .count();
    }

    @Override
    protected String getDay() {

        return "day02";
    }
}
