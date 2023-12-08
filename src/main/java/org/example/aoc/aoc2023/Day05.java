package org.example.aoc.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

class Day05 extends AoC2023Day<Day05.Almanac> {

    private record Range(long min, long max) {}

    private record MapLine(long destination, long source, long range) {}

    protected record Almanac(List<Long> seeds, List<Range> seedRanges, List<List<MapLine>> maps) {}

    @Override
    protected Almanac parseInput(String strInput) {

        final String[] split = strInput.split(System.lineSeparator() + System.lineSeparator());

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

        final ArrayList<Range> rangesForNextMap = new ArrayList<>();

        for (Range r : ranges) {

            final HashSet<Range> remainderRanges = new HashSet<>();
            remainderRanges.add(r);

            for (MapLine mapLine : mapLines) {

                for (Range range : new HashSet<>(remainderRanges)) {

                    if (range.min >= mapLine.source && range.max < mapLine.source + mapLine.range) {
                        rangesForNextMap.add(new Range(mapLine.destination + (range.min - mapLine.source),
                                                       mapLine.destination + (range.max - mapLine.source)));

                        remainderRanges.remove(range);
                        break;
                    } else if (range.min >= mapLine.source && range.min < mapLine.source + mapLine.range) {
                        rangesForNextMap.add(new Range(mapLine.destination + (range.min - mapLine.source),
                                                       mapLine.destination + mapLine.range - 1));

                        remainderRanges.remove(range);
                        remainderRanges.add(new Range(mapLine.source + mapLine.range, range.max));
                    } else if (range.max >= mapLine.source && range.max < mapLine.source + mapLine.range) {
                        rangesForNextMap.add(new Range(mapLine.destination,
                                                       mapLine.destination + (range.max - mapLine.source)));

                        remainderRanges.remove(range);
                        remainderRanges.add(new Range(range.min, mapLine.source - 1));
                    } else if (range.min < mapLine.source && range.max > mapLine.source + mapLine.range) {
                        rangesForNextMap.add(new Range(mapLine.destination,
                                                       mapLine.destination + mapLine.range - 1));

                        remainderRanges.remove(range);
                        remainderRanges.add(new Range(range.min, mapLine.source - 1));
                        remainderRanges.add(new Range(mapLine.source + mapLine.range, range.max));
                    }
                }
            }

            rangesForNextMap.addAll(remainderRanges);
        }

        return rangesForNextMap;
    }

    @Override
    protected String getDay() {

        return "day05";
    }
}
