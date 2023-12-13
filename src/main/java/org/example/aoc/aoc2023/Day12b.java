package org.example.aoc.aoc2023;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class Day12b extends AoC2023Day<List<Day12b.Spring>> {

    protected record Spring(String row, List<Integer> groups) {
    }

    @Override
    protected List<Spring> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> {
                    final String[] split = line.split(" ");
                    final String row = split[0];
                    final List<Integer> groups = Arrays.stream(split[1].split(",")).map(Integer::parseInt).toList();

                    return new Spring(row, groups);
                })
                .toList();
    }

    @Override
    protected Integer partOne(List<Spring> input) {

        int s = 0;
        for (Spring spring : input) {

            final Instant start = Instant.now();

            long rec = rec(Arrays.stream(spring.row.split("\\.")).filter(b -> !b.isEmpty()).toList(), spring.groups);
            System.out.println(rec);
            s += rec;

            final Instant end = Instant.now();

            final long l = Duration.between(start, end).toMillis();

        }

        return s;
    }

    private record Pair(List<String> rows, List<Integer> groups) {}

    private long rec(List<String> rows, List<Integer> groups) {

        System.out.println(rows + " | " + groups);

        if (groups.isEmpty()) {

            if (rows.stream().anyMatch(row -> row.chars().anyMatch(c -> c == '#'))) {
                return 0;
            } else {
                return 1;
            }
        }

        if (rows.isEmpty()) {

            return 0;
        }

        final String row = rows.getFirst();
        final int group = groups.getFirst();

        if (row.length() == group) {

            if (row.chars().allMatch(c -> c == '?')) {

                return rec(rows.subList(1, rows.size()), groups.subList(1, groups.size())) + rec(rows.subList(1, rows.size()), groups);
            } else {

                return rec(rows.subList(1, rows.size()), groups.subList(1, groups.size()));
            }
        }

        if (group > row.length()) {

            if (row.chars().anyMatch(c -> c == '#')) {
                return 0;
            } else {
                return rec(rows.subList(1, rows.size()), groups);
            }
        }

        if (group == 0) {
            if (row.contains("#")) {
                return 0;
            } else {
                final List<String> list = Stream.concat(
                        row.substring(1).lines(),
                        rows.subList(1, rows.size()).stream()
                ).toList();
                return rec(list, groups.subList(1, groups.size()));
            }
        }

        String block = row.substring(0, group);

        if (block.startsWith("#")) {
            final char offChar = row.charAt(group);

            if (offChar == '#') {

                return 0;
            }

            final List<String> list = Stream.concat(
                    row.substring(group + 1).lines(),
                    rows.subList(1, rows.size()).stream()
            ).toList();

            return rec(list, groups.subList(1, groups.size()));
        } else {
            final char offChar = row.charAt(group);

            if (offChar == '#') {

                final List<String> list = Stream.concat(
                        row.substring(1).lines(),
                        rows.subList(1, rows.size()).stream()
                ).toList();

                return rec(list, groups);
            }

            final List<String> list = Stream.concat(
                    row.substring(group + 1).lines(),
                    rows.subList(1, rows.size()).stream()
            ).toList();

            final List<String> list2 = Stream.concat(
                    row.substring(1).lines(),
                    rows.subList(1, rows.size()).stream()
            ).toList();

            return rec(list, groups.subList(1, groups.size())) + rec(list2, groups);
        }
    }

    @Override
    protected Long partTwo(List<Spring> input) {

        if(true)return 1L;

        long s = 0;

        // Brute force
        for (Spring spring : input) {

            System.out.println(spring.row);

            StringBuilder sb = new StringBuilder();
            ArrayList<Integer> a = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                sb.append(spring.row);
                sb.append("?");
                a.addAll(spring.groups);
            }
            sb.append(spring.row);
            a.addAll(spring.groups);

            long bb = rec(Arrays.stream(sb.toString().split("\\.")).filter(b -> !b.isEmpty()).toList(), a);
            s += bb;

            System.out.println("Brute force: " + bb);

            s = 0;

            BigInteger z = BigInteger.ONE;

            long c = rec(Arrays.stream(spring.row.split("\\.")).filter(b -> !b.isEmpty()).toList(), spring.groups);

            String newS = spring.row + "?" + spring.row;
            ArrayList<Integer> a1 = new ArrayList<>();
            a1.addAll(spring.groups);
            a1.addAll(spring.groups);

            bb = rec(Arrays.stream(newS.toString().split("\\.")).filter(b -> !b.isEmpty()).toList(), a1);
            s += (long) (c * Math.pow((bb / c), 4));

            BigInteger div = BigInteger.valueOf(bb).divide(BigInteger.valueOf(c));

            BigInteger multiply = BigInteger.valueOf(c).multiply(div.pow(4));
            z = z.add(
                    multiply
            );
            System.out.println("Calculation: " + multiply);
        }

        s = 0;

        BigInteger z = BigInteger.ONE;

        for (Spring spring : input) {

            /*int c = rec(Arrays.stream(spring.row.split("\\.")).filter(b -> !b.isEmpty()).toList(), spring.groups);

            String newS = "?" + spring.row;

            int bb = rec(Arrays.stream(newS.toString().split("\\.")).filter(b -> !b.isEmpty()).toList(), spring.groups);
            s = (long) (c * Math.pow((bb / c), 4));*/

           /* long c = rec(Arrays.stream(spring.row.split("\\.")).filter(b -> !b.isEmpty()).toList(), spring.groups);

            String newS = spring.row + "?" + spring.row;
            ArrayList<Integer> a1 = new ArrayList<>();
            a1.addAll(spring.groups);
            a1.addAll(spring.groups);

            long bb = rec(Arrays.stream(newS.toString().split("\\.")).filter(b -> !b.isEmpty()).toList(), a1);
            s += (long) (c * Math.pow((bb / c), 4));

            BigInteger div = BigInteger.valueOf(bb).divide(BigInteger.valueOf(c));

            BigInteger multiply = BigInteger.valueOf(c).multiply(div.pow(4));
            z = z.add(
                    multiply
            );
            System.out.println("Calculation: " + multiply);*/

            String newS = "?" + spring.row;

            long second = rec(Arrays.stream(newS.toString().split("\\.")).filter(b -> !b.isEmpty()).toList(), spring.groups);
            System.out.println("Second: " + second);

            for (int ii = 1; ii <= 5; ii++) {
                StringBuilder sb = new StringBuilder();
                ArrayList<Integer> a = new ArrayList<>();

                for (int i = 0; i < ii-1; i++) {
                    sb.append(spring.row);
                    sb.append("?");
                    a.addAll(spring.groups);
                }
                sb.append(spring.row);
                a.addAll(spring.groups);

                long bbb = rec(Arrays.stream(sb.toString().split("\\.")).filter(b -> !b.isEmpty()).toList(), a);
                System.out.println(bbb);
            }
        }

        // 28796504752339 -> WRONG
        // 7373687652768 -> Too low
        return s;
    }

    @Override
    protected String getDay() {

        return "day12";
    }
}
