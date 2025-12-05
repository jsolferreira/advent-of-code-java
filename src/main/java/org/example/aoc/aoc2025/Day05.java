package org.example.aoc.aoc2025;

import org.example.aoc.utils.Pair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

class Day05 extends AoC2025Day<Pair<List<Day05.Range>, List<Long>>> {

    protected static class Range implements Comparable<Range> {

        private long start;

        private long end;

        public Range(long start, long end) {

            this.start = start;
            this.end = end;
        }

        public long getStart() {

            return start;
        }

        public void setStart(long start) {

            this.start = start;
        }

        public long getEnd() {

            return end;
        }

        public void setEnd(long end) {

            this.end = end;
        }

        @Override
        public int compareTo(Range o) {

            final int startCompared = Long.compare(this.getStart(), o.getStart());

            return startCompared != 0 ? startCompared : Long.compare(this.getEnd(), o.getEnd());
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            Range range = (Range) obj;

            return this.getStart() == range.getStart() && this.getEnd() == range.getEnd();
        }

        @Override
        public int hashCode() {

            return Long.hashCode(this.getStart()) * Long.hashCode(this.getEnd());
        }

        @Override
        public String toString() {

            return this.getStart() + "-" + this.getEnd();
        }
    }

    @Override
    protected Pair<List<Day05.Range>, List<Long>> parseInput(String strInput) {

        final String[] split = strInput.split(System.lineSeparator() + System.lineSeparator());

        final List<Range> ranges = Arrays.stream(split[0].split(System.lineSeparator()))
                .map(line -> {
                    final String[] range = line.split("-");

                    return new Range(Long.parseLong(range[0]), Long.parseLong(range[1]));
                })
                .toList();

        final List<Long> ids = Arrays.stream(split[1].split(System.lineSeparator()))
                .map(Long::parseLong)
                .toList();

        return new Pair<>(ranges, ids);
    }

    @Override
    protected Long partOne(Pair<List<Day05.Range>, List<Long>> input) {

        final List<Range> ranges = input.left();
        final List<Long> ids = input.right();

        long count = 0;

        for (Long id : ids) {
            for (Range range : ranges) {
                if (id >= range.getStart() && id <= range.getEnd()) {
                    count++;
                    break;
                }
            }
        }

        return count;
    }

    @Override
    protected Long partTwo(Pair<List<Day05.Range>, List<Long>> input) {

        final Set<Range> ranges = new TreeSet<>(input.left());

        final HashSet<Range> mergedRanges = new HashSet<>();

        for (Range range : ranges) {

            boolean merged = false;

            for (Range mergedRange : mergedRanges) {
                if (canMergeRange(range, mergedRange)) {
                    mergedRange.setStart(Math.min(range.getStart(), mergedRange.getStart()));
                    mergedRange.setEnd(Math.max(range.getEnd(), mergedRange.getEnd()));
                    merged = true;
                    break;
                }
            }

            if (!merged) {
                mergedRanges.add(range);
            }
        }

        return mergedRanges.stream()
                .reduce(0L, (acc, val) -> acc + val.getEnd() - val.getStart() + 1, Long::sum);
    }

    private boolean canMergeRange(Range range1, Range range2) {

        return !(range1.getEnd() < range2.getStart() || range1.getStart() > range2.getEnd());
    }

    @Override
    protected String getDay() {

        return "day05";
    }
}
