package org.example.aoc.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Day05 extends AoC2023Day<Day05.Almanac> {

    private record Range(long min, long max) {}

    private record MapLine(long destination, long source, long range) {}

    protected record Almanac(List<Long> seeds, List<Range> seedRanges, List<List<MapLine>> maps) {}

    @Override
    protected Almanac parseInput(String strInput) {

        final String[] split = strInput.split("\n\n");

        List<Long> seeds = parseSeeds(split[0]);
        List<Range> seedRanges = parseSeedRanges(seeds);
        List<List<MapLine>> mapLines = new ArrayList<>();

        for (int i = 1; i < split.length; i++) {

            mapLines.add(parseMaps(split[i]));
        }

        return new Almanac(seeds, seedRanges, mapLines);
    }

    private List<Long> parseSeeds(String s) {

        return Arrays.stream(s.split(":")[1].trim().split("\\s+")).map(Long::parseLong).toList();
    }

    private List<Range> parseSeedRanges(List<Long> seeds) {

        List<Range> seedRanges = new ArrayList<>();

        for (int i = 0; i < seeds.size(); i += 2) {
            seedRanges.add(new Range(seeds.get(i), seeds.get(i) + seeds.get(i + 1) - 1));
        }

        return seedRanges;
    }

    private List<MapLine> parseMaps(String s) {

        return Arrays.stream(s.split("\n"))
                .skip(1)
                .map(line -> {
                    Long[] list = Arrays.stream(line.split("\\s+")).map(Long::parseLong).toArray(Long[]::new);

                    return new MapLine(list[0], list[1], list[2]);
                })
                .toList();
    }

    @Override
    protected Long partOne(Almanac input) {

        long min = Long.MAX_VALUE;

        for (Long seed : input.seeds) {

            long f = seed;

            for (List<MapLine> map : input.maps) {

                for (MapLine mapLine : map) {

                    if (mapLine.source <= f && mapLine.source + mapLine.range > f) {
                        f = mapLine.destination + (f - mapLine.source);
                        break;
                    }
                }
            }

            min = Math.min(f, min);
        }

        return min;
    }

    @Override
    protected Long partTwo(Almanac input) {

        long min = Long.MAX_VALUE;

        for (Range seedRange : input.seedRanges) {

            List<Range> ranges = List.of(seedRange);

            for (List<MapLine> map : input.maps) {

                ranges = checkIntersection(ranges, map);
            }

            long finalMin = min;
            min = ranges.stream().map(m -> m.min).min(Comparator.comparingLong(x -> x)).filter(m -> m < finalMin).orElse(min);
        }

        return min;
    }

    private List<Range> checkIntersection(List<Range> ranges, List<MapLine> mapLines) {

        ArrayList<Range> fRanges = new ArrayList<>();

        for (Range r : ranges) {

            List<Range> remainingRanges = List.of(r);
            ArrayList<Range> ffRanges = new ArrayList<>();

            for (MapLine mapLine : mapLines) {

                for (Range range : remainingRanges) {

                    if (range.min >= mapLine.source && range.max < mapLine.source + mapLine.range) {
                        fRanges.add(new Range(mapLine.destination + (range.min - mapLine.source),
                                              mapLine.destination + (range.max - mapLine.source)));
                        ffRanges = remainingRanges.stream().filter(x -> x != range).collect(Collectors.toCollection(ArrayList::new));
                        break;
                    } else if (range.min >= mapLine.source && range.min < mapLine.source + mapLine.range) {
                        fRanges.add(new Range(mapLine.destination + (range.min - mapLine.source),
                                              mapLine.destination + mapLine.range - 1));

                        ffRanges = remainingRanges.stream().filter(x -> x != range).collect(Collectors.toCollection(ArrayList::new));
                        ffRanges.add(new Range(mapLine.source + mapLine.range, range.max));
                    } else if (range.max >= mapLine.source && range.max < mapLine.source + mapLine.range) {
                        fRanges.add(new Range(mapLine.destination,
                                              mapLine.destination + (range.max - mapLine.source)));

                        ffRanges = remainingRanges.stream().filter(x -> x != range).collect(Collectors.toCollection(ArrayList::new));
                        ffRanges.add(new Range(range.min, mapLine.source - 1));
                    } else if (range.min < mapLine.source && range.max > mapLine.source + mapLine.range) {
                        fRanges.add(new Range(mapLine.destination,
                                              mapLine.destination + mapLine.range - 1));

                        ffRanges = remainingRanges.stream().filter(x -> x != range).collect(Collectors.toCollection(ArrayList::new));
                        ffRanges.add(new Range(range.min, mapLine.source - 1));
                        ffRanges.add(new Range(mapLine.source + mapLine.range, range.max));
                    }
                }
                if (!ffRanges.isEmpty()) {
                    remainingRanges = (List<Range>) ffRanges.clone();
                }
            }

            if (!ffRanges.isEmpty()) {
                fRanges.addAll(ffRanges);
            } else if (fRanges.isEmpty()) {
                fRanges.addAll(remainingRanges);
            }
        }

        return fRanges;
    }

    @Override
    protected String getDay() {

        return "day05";
    }
}
