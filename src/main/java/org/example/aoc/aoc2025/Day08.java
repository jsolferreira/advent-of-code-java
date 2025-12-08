package org.example.aoc.aoc2025;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

class Day08 extends AoC2025Day<List<Day08.Position>> {

    protected record Position(int x, int y, int z) {}

    private record Distance(Position p, Position q, double distance) implements Comparable<Distance> {

        @Override
        public int compareTo(Distance o) {

            return Double.compare(distance, o.distance());
        }
    }

    @Override
    protected List<Position> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> line.split(","))
                .map(split -> new Position(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])))
                .toList();
    }

    @Override
    protected Integer partOne(List<Position> input) {

        final TreeSet<Distance> distances = new TreeSet<>();

        for (int i = 0; i < input.size() - 1; i++) {

            double max = Double.MAX_VALUE;
            final Position p = input.get(i);
            for (int j = i + 1; j < input.size(); j++) {

                final Position q = input.get(j);

                final double dist = distance(p, q);

                if (distances.size() == 1000) {

                    if (dist < max) {
                        distances.pollLast();
                        distances.add(new Distance(p, q, dist));
                        max = distances.last().distance();
                    }
                } else {

                    distances.add(new Distance(p, q, dist));
                }
            }
        }

        final List<Set<Position>> grouped = new ArrayList<>();

        for (Distance distance : distances) {

            if (grouped.isEmpty()) {
                final Set<Position> firstGroup = new HashSet<>();
                firstGroup.add(distance.p());
                firstGroup.add(distance.q());
                grouped.add(firstGroup);
            } else {

                Integer[] array = IntStream.range(0, grouped.size())
                        .filter(i -> grouped.get(i).contains(distance.p()) || grouped.get(i).contains(distance.q()))
                        .boxed()
                        .sorted((f1, f2) -> Long.compare(f2, f1))
                        .toArray(Integer[]::new);

                if (array.length == 0) {
                    final Set<Position> newGroup = new HashSet<>();
                    newGroup.add(distance.p());
                    newGroup.add(distance.q());
                    grouped.add(newGroup);
                } else {
                    Set<Position> newGroup = new HashSet<>();
                    for (int i : array) {
                        newGroup.addAll(grouped.get(i));
                        grouped.remove(i);
                    }
                    newGroup.add(distance.p());
                    newGroup.add(distance.q());
                    grouped.add(newGroup);
                }
            }
        }

        return grouped.stream()
                .map(Set::size)
                .sorted((f1, f2) -> Long.compare(f2, f1))
                .limit(3)
                .reduce((a, b) -> a * b)
                .orElse(0);
    }

    @Override
    protected Integer partTwo(List<Position> input) {

        final TreeSet<Distance> distances = new TreeSet<>();

        for (int i = 0; i < input.size() - 1; i++) {

            final Position p = input.get(i);
            for (int j = i + 1; j < input.size(); j++) {

                final Position q = input.get(j);

                final double dist = distance(p, q);

                distances.add(new Distance(p, q, dist));
            }
        }

        final List<Set<Position>> grouped = new ArrayList<>();
        Distance connectingBoxes = null;

        for (Distance distance : distances) {

            if (grouped.isEmpty()) {
                final Set<Position> firstGroup = new HashSet<>();
                firstGroup.add(distance.p());
                firstGroup.add(distance.q());
                grouped.add(firstGroup);
            } else {

                Integer[] array = IntStream.range(0, grouped.size())
                        .filter(i -> grouped.get(i).contains(distance.p()) || grouped.get(i).contains(distance.q()))
                        .boxed()
                        .sorted((f1, f2) -> Long.compare(f2, f1))
                        .toArray(Integer[]::new);

                if (array.length == 0) {
                    final Set<Position> newGroup = new HashSet<>();
                    newGroup.add(distance.p());
                    newGroup.add(distance.q());
                    grouped.add(newGroup);
                } else {
                    Set<Position> newGroup = new HashSet<>();
                    for (int i : array) {
                        newGroup.addAll(grouped.get(i));
                        grouped.remove(i);
                    }
                    newGroup.add(distance.p());
                    newGroup.add(distance.q());
                    grouped.add(newGroup);

                    if (newGroup.size() == input.size()) {
                        connectingBoxes = distance;
                        break;
                    }
                }
            }
        }

        return connectingBoxes == null
                ? 0
                : connectingBoxes.p().x() * connectingBoxes.q().x();
    }

    private double distance(Position p, Position q) {

        final double x = (double) p.x() - q.x();
        final double y = (double) p.y() - q.y();
        final double z = (double) p.z() - q.z();

        return Math.sqrt(x * x + y * y + z * z);
    }

    @Override
    protected String getDay() {

        return "day08";
    }
}
