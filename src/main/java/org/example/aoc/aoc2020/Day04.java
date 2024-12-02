package org.example.aoc.aoc2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Day04 extends AoC2020Day<List<Day04.Passport>> {

    protected record Passport(Map<String, String> fields) {
    }

    private final Map<String, Function<String, Boolean>> passportFields = Map.ofEntries(
            Map.entry("byr", Day04::validateByr),
            Map.entry("iyr", Day04::validateIyr),
            Map.entry("eyr", Day04::validateEyr),
            Map.entry("hgt", Day04::validateHgt),
            Map.entry("hcl", Day04::validateHcl),
            Map.entry("ecl", Day04::validateEcl),
            Map.entry("pid", Day04::validatePid)
    );

    private final Set<String> notRequiredPassportFields = Set.of("cid");

    private static final Pattern hgtPattern = Pattern.compile("(\\d+)(cm|in)");

    private static final Pattern hclPattern = Pattern.compile("#(\\d|[a-f]){6}");

    private static final Pattern pidPattern = Pattern.compile("\\d{9}");

    private static final Set<String> eyeColors = Set.of("amb",
                                                        "blu",
                                                        "brn",
                                                        "gry",
                                                        "grn",
                                                        "hzl",
                                                        "oth");

    @Override
    protected List<Passport> parseInput(String strInput) {

        return strInput.lines()
                .<List<Map<String, String>>>reduce(new ArrayList<>(), (acc, val) -> {

                    if (val.isBlank()) {

                        acc.add(new HashMap<>());
                    } else {

                        final Map<String, String> fields = Arrays.stream(val.split(" "))
                                .map(f -> f.split(":"))
                                .collect(Collectors.toMap(f -> f[0], f -> f[1]));

                        if (acc.isEmpty()) {

                            acc.add(fields);
                        } else {

                            final int lastIndex = acc.size() - 1;
                            final Map<String, String> lastElement = acc.get(lastIndex);

                            lastElement.putAll(fields);
                        }
                    }

                    return acc;
                }, (a, b) -> new ArrayList<>()).stream()
                .map(Passport::new)
                .toList();
    }

    @Override
    protected Long partOne(List<Passport> input) {

        return input.stream()
                .filter(this::passportHasAllRequiredFields)
                .count();
    }

    @Override
    protected Long partTwo(List<Passport> input) {

        return input.stream()
                .filter(passport -> passportHasAllRequiredFields(passport) && passportHasValidValues(passport))
                .count();
    }

    private boolean passportHasAllRequiredFields(Passport passport) {

        return passport.fields().keySet().containsAll(this.passportFields.keySet());
    }

    private boolean passportHasValidValues(Passport passport) {

        return passport.fields
                .entrySet()
                .stream()
                .filter(entry -> !notRequiredPassportFields.contains(entry.getKey()))
                .allMatch(entry -> this.passportFields.get(entry.getKey()).apply(entry.getValue()));
    }

    @Override
    protected String getDay() {

        return "day04";
    }

    private static boolean validateByr(String s) {

        final int n = Integer.parseInt(s);

        return n >= 1920 && n <= 2002;
    }

    private static boolean validateIyr(String s) {

        final int n = Integer.parseInt(s);

        return n >= 2010 && n <= 2020;
    }

    private static boolean validateEyr(String s) {

        final int n = Integer.parseInt(s);

        return n >= 2020 && n <= 2030;
    }

    private static boolean validateHgt(String s) {

        final Matcher matcher = hgtPattern.matcher(s);

        if (matcher.matches()) {

            final int n = Integer.parseInt(matcher.group(1));
            final String unit = matcher.group(2);

            if (unit.equals("in")) {

                return n >= 59 && n <= 76;
            } else {

                return n >= 150 && n <= 193;
            }
        } else {

            return false;
        }
    }

    private static boolean validateHcl(String s) {

        return hclPattern.matcher(s).matches();
    }

    private static boolean validateEcl(String s) {

        return eyeColors.contains(s);
    }

    private static boolean validatePid(String s) {

        return pidPattern.matcher(s).matches();
    }
}
