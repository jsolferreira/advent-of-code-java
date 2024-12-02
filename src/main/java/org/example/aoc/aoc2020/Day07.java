package org.example.aoc.aoc2020;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day07 extends AoC2020Day<Map<String, Map<String, Integer>>> {

    private static final String SHINY_GOLD = "shiny gold";

    @Override
    protected Map<String, Map<String, Integer>> parseInput(String strInput) {

        final Pattern pattern = Pattern.compile("((?:(\\d+) ((?:\\w+ )*\\w+)+ )+),?");

        return strInput
                .lines()
                .map(line -> {
                    final String[] split = line.split(" bags contain ");

                    final String outerBagName = split[0];

                    final Matcher matcher = pattern.matcher(split[1]);

                    final Map<String, Integer> innerBags = IntStream.iterate(0, i -> matcher.find(), i -> i + 1)
                            .mapToObj(i -> Map.entry(matcher.group(3), Integer.parseInt(matcher.group(2))))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                    return Map.entry(outerBagName, innerBags);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    protected Long partOne(Map<String, Map<String, Integer>> input) {

        final HashMap<String, Boolean> visitedBags = new HashMap<>();

        return input.entrySet().stream()
                .filter(entry -> containsShinyGold(visitedBags, input, entry.getKey(), entry.getValue().keySet()))
                .count();
    }

    private boolean containsShinyGold(Map<String, Boolean> visitedBags,
                                      Map<String, Map<String, Integer>> input,
                                      String outerBag,
                                      Set<String> innerBags) {

        if (innerBags.isEmpty() || outerBag.equals(SHINY_GOLD)) {

            visitedBags.put(outerBag, false);

            return false;
        } else if (visitedBags.containsKey(outerBag)) {

            return visitedBags.get(outerBag);
        }

        final boolean containsShinyGold = innerBags.stream().anyMatch(innerBag -> innerBag.equals(SHINY_GOLD) ||
                containsShinyGold(visitedBags,
                                  input,
                                  innerBag,
                                  input.get(innerBag).keySet()));

        visitedBags.put(outerBag, containsShinyGold);

        return containsShinyGold;
    }

    @Override
    protected Long partTwo(Map<String, Map<String, Integer>> input) {

        final HashMap<String, Long> visitedBags = new HashMap<>();

        return countBags(visitedBags, input, SHINY_GOLD, input.get(SHINY_GOLD));
    }

    private long countBags(Map<String, Long> visitedBags,
                           Map<String, Map<String, Integer>> input,
                           String outerBag,
                           Map<String, Integer> innerBags) {

        if (innerBags.isEmpty()) {

            visitedBags.put(outerBag, 0L);

            return 0;
        } else if (visitedBags.containsKey(outerBag)) {

            return visitedBags.get(outerBag);
        }

        return innerBags.entrySet().stream()
                .reduce(0L,
                        (acc, val) -> {
                            acc += val.getValue() + val.getValue() * countBags(visitedBags,
                                                                               input,
                                                                               val.getKey(),
                                                                               input.get(val.getKey()));
                            visitedBags.put(outerBag, acc);

                            return acc;
                        },
                        Long::sum);
    }

    @Override
    protected String getDay() {

        return "day07";
    }
}
