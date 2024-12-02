package org.example.aoc.aoc2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day15 extends AoC2022Day<Map<Day15.Position, Day15.Position>> {

    protected record Range(int start, int end) {
    }

    protected record Position(int x, int y) {
    }

    @Override
    protected Map<Position, Position> parseInput(String strInput) {

        final Pattern pattern = Pattern.compile("x=(-?\\d+), y=(-?\\d+).*x=(-?\\d+), y=(-?\\d+)");

        return strInput.lines()
                .map(pattern::matcher)
                .filter(Matcher::find)
                .map(matcher -> Map.entry(new Position(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))),
                                          new Position(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)))))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    protected Long partOne(Map<Position, Position> input) {

        final Map<Integer, Boolean> rowMap = new HashMap<>();

        for (Position value : input.values()) {
            if (value.y == 2000000) {

                rowMap.put(value.x, true);
            }
        }

        for (Map.Entry<Position, Position> entry : input.entrySet()) {

            final Position sensor = entry.getKey();
            final Position beacon = entry.getValue();
            final int manhattanDistance = getManhattanDistance(sensor, beacon);

            if (sensor.y + manhattanDistance >= 2000000 || sensor.y - manhattanDistance <= 2000000) {

                rowMap.putIfAbsent(sensor.x, false);

                for (int x = sensor.x + 1; getManhattanDistance(sensor, new Position(x, 2000000)) <= manhattanDistance; x++) {

                    rowMap.putIfAbsent(x, false);
                }

                for (int x = sensor.x - 1; getManhattanDistance(sensor, new Position(x, 2000000)) <= manhattanDistance; x--) {

                    rowMap.putIfAbsent(x, false);
                }
            }
        }

        return rowMap.values().stream().filter(v -> !v).count();
    }

    private int getManhattanDistance(Position position1, Position position2) {

        return Math.abs(position1.x - position2.x) + Math.abs(position1.y - position2.y);
    }

    @Override
    protected Long partTwo(Map<Position, Position> input) {

        final int min = 0;
        final int max = 20 - 1;

        final Map<Range, Range[]> r = new HashMap<>();
        final Range[] rr = new Range[1];
        rr[0] = new Range(min, max);
        r.put(new Range(min, max), rr);

        for (Position beacon : input.values()) {
            if (beacon.x > min && beacon.x < max && beacon.y > min && beacon.y < max) {

                Optional<Range> first = r.keySet().stream().filter(x -> x.start <= beacon.y && x.end >= beacon.y).findFirst();

                if (first.isPresent()) {

                    Range[] newRange = split(List.of(first.get()).toArray(Range[]::new), new Range(beacon.y, beacon.y));

                    Range[] ranges = r.get(first.get());
                    r.remove(first.get());

                    for (Range range : newRange) {
                        r.put(range, ranges);
                    }

                    newRange = split(ranges, new Range(beacon.x, beacon.x));

                    r.put(new Range(beacon.y, beacon.y), newRange);
                }
            }
        }

        final Range[][] ranges = new Range[max + 1][1];

        for (Range[] range : ranges) {
            range[0] = new Range(min, max);
        }

        for (Position beacon : input.values()) {
            if (beacon.x > min && beacon.x < max && beacon.y > min && beacon.y < max) {
                ranges[beacon.y] = new Range[2];
                ranges[beacon.y][0] = new Range(min, beacon.x - 1);
                ranges[beacon.y][1] = new Range(beacon.x + 1, max);
            }
        }

        for (Map.Entry<Position, Position> entry : input.entrySet()) {

            final Position sensor = entry.getKey();
            final Position beacon = entry.getValue();
            final int manhattanDistance = getManhattanDistance(sensor, beacon);

            int minYWithoutBeacon = sensor.y - manhattanDistance;
            int maxYWithoutBeacon = sensor.y + manhattanDistance;

            int minYWithoutBeaconInRange = Math.max(minYWithoutBeacon, min);
            int maxYWithoutBeaconInRange = Math.min(maxYWithoutBeacon, max);

            int x = minYWithoutBeaconInRange - minYWithoutBeacon;
            for (int y = minYWithoutBeaconInRange; y <= maxYWithoutBeaconInRange; y++) {

                ranges[y] = split(ranges[y], new Range(sensor.x - x, sensor.x + x));

                if (y >= sensor.y) {
                    x--;
                } else {
                    x++;
                }
            }
        }

        return IntStream.range(0, ranges.length)
                .boxed()
                .flatMapToLong(y -> IntStream.range(0, ranges[y].length)
                        .findFirst()
                        .stream().mapToLong(x -> ranges[y][x].start * 4000000L + y))
                .findFirst()
                .orElseThrow();
    }

    private Range[] split(Range[] ranges, Range p) {

        final List<Range> newRanges = new ArrayList<>();

        for (Range range : ranges) {

            if (p.end < range.start || p.start > range.end) {

                newRanges.add(range);
                continue;
            }

            if (p.start >= range.start && p.end <= range.end) {

                if (p.start - 1 >= range.start) {

                    newRanges.add(new Range(range.start, p.start - 1));
                }

                if (p.end + 1 <= range.end) {

                    newRanges.add(new Range(p.end + 1, range.end));
                }
            } else if (p.start <= range.start) {

                if (p.end + 1 <= range.end) {

                    newRanges.add(new Range(p.end + 1, range.end));
                }
            } else if (p.end >= range.end && p.start - 1 >= range.start) {

                newRanges.add(new Range(range.start, p.start - 1));
            }
        }

        return newRanges.toArray(Range[]::new);
    }

    @Override
    protected String getDay() {

        return "day15";
    }
}
