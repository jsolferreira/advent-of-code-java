package org.example.aoc.aoc2015;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class Day09 extends AoC2015Day<List<Day09.Distance>> {

    protected record Distance(String from, String to, int value) {
    }

    @Override
    protected List<Distance> parseInput(String strInput) {

        final Pattern pattern = Pattern.compile("(\\w+) to (\\w+) = (\\d+)");

        return strInput.lines()
                .map(pattern::matcher)
                .filter(Matcher::find)
                .map(matcher -> new Distance(matcher.group(1), matcher.group(2), Integer.parseInt(matcher.group(3))))
                .toList();
    }

    @Override
    protected Integer partOne(List<Distance> input) {

        final List<String> cities = getDistinctCities(input);

        return getDistanceCombinations(input, cities, null).stream()
                .min(Integer::compareTo)
                .orElseThrow();
    }

    @Override
    protected Integer partTwo(List<Distance> input) {

        final List<String> cities = getDistinctCities(input);

        return getDistanceCombinations(input, cities, null).stream()
                .max(Integer::compareTo)
                .orElseThrow();
    }

    private List<String> getDistinctCities(List<Distance> input) {

        return Stream.concat(input.stream().map(Distance::from),
                             input.stream().map(Distance::to))
                .distinct()
                .toList();
    }

    private List<Integer> getDistanceCombinations(List<Distance> distances, List<String> cities, String fromCity) {

        if (cities.size() == 0) {

            return List.of(0);
        }

        return cities.stream()
                .flatMap(toCity -> {

                    final List<String> otherCities = cities.stream()
                            .filter(city -> !city.equals(toCity))
                            .toList();

                    final List<Integer> toOtherCitiesDistances = getDistanceCombinations(distances, otherCities, toCity);

                    final int distanceBetweenCities = Optional.ofNullable(fromCity)
                            .flatMap(distance -> distances.stream()
                                    .filter(d -> (d.from.equals(toCity) && d.to.equals(fromCity)) ||
                                            (d.to.equals(toCity) && d.from.equals(fromCity)))
                                    .findFirst()
                                    .map(Distance::value))
                            .orElse(0);

                    return toOtherCitiesDistances.stream().map(distance -> distance + distanceBetweenCities);
                })
                .toList();
    }

    @Override
    protected String getDay() {

        return "day09";
    }
}
