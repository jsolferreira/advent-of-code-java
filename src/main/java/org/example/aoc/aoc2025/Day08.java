package org.example.aoc.aoc2025;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

class Day08 extends AoC2025Day<List<Day08.JunctionBox>> {

    protected record JunctionBox(int x, int y, int z) {}

    private record Distance(JunctionBox junctionBox1, JunctionBox junctionBox2, double distance) implements Comparable<Distance> {

        @Override
        public int compareTo(Distance o) {

            return Double.compare(distance, o.distance());
        }
    }

    @Override
    protected List<JunctionBox> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> line.split(","))
                .map(split -> new JunctionBox(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])))
                .toList();
    }

    @Override
    protected Integer partOne(List<JunctionBox> input) {

        final TreeSet<Distance> distances = new TreeSet<>();

        for (int i = 0; i < input.size() - 1; i++) {

            double max = Double.MAX_VALUE;
            final JunctionBox p = input.get(i);
            for (int j = i + 1; j < input.size(); j++) {

                final JunctionBox q = input.get(j);

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

        final List<Set<JunctionBox>> circuits = new ArrayList<>();

        for (Distance distance : distances) {

            final Set<JunctionBox> circuit = new HashSet<>();

            if (!circuits.isEmpty()) {

                final List<Set<JunctionBox>> matchingCircuits = circuits.stream()
                        .filter(group -> group.contains(distance.junctionBox1()) || group.contains(distance.junctionBox2()))
                        .toList();

                for (Set<JunctionBox> group : matchingCircuits) {
                    circuit.addAll(group);
                    circuits.remove(group);
                }
            }

            circuit.add(distance.junctionBox1());
            circuit.add(distance.junctionBox2());
            circuits.add(circuit);
        }

        return circuits.stream()
                .map(Set::size)
                .sorted((f1, f2) -> Long.compare(f2, f1))
                .limit(3)
                .reduce((a, b) -> a * b)
                .orElse(0);
    }

    @Override
    protected Integer partTwo(List<JunctionBox> input) {

        final TreeSet<Distance> distances = new TreeSet<>();

        for (int i = 0; i < input.size() - 1; i++) {

            final JunctionBox p = input.get(i);
            for (int j = i + 1; j < input.size(); j++) {

                final JunctionBox q = input.get(j);

                final double dist = distance(p, q);

                distances.add(new Distance(p, q, dist));
            }
        }

        final List<Set<JunctionBox>> circuits = new ArrayList<>();
        Distance desiredJunctionBoxes = null;

        for (Distance distance : distances) {

            final Set<JunctionBox> circuit = new HashSet<>();

            if (!circuits.isEmpty()) {

                final List<Set<JunctionBox>> matchingCircuits = circuits.stream()
                        .filter(group -> group.contains(distance.junctionBox1()) || group.contains(distance.junctionBox2()))
                        .toList();

                for (Set<JunctionBox> group : matchingCircuits) {
                    circuit.addAll(group);
                    circuits.remove(group);
                }
            }

            circuit.add(distance.junctionBox1());
            circuit.add(distance.junctionBox2());
            circuits.add(circuit);

            if (circuit.size() == input.size()) {
                desiredJunctionBoxes = distance;
                break;
            }
        }

        return desiredJunctionBoxes == null
                ? 0
                : desiredJunctionBoxes.junctionBox1().x() * desiredJunctionBoxes.junctionBox2().x();
    }

    private double distance(JunctionBox p, JunctionBox q) {

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
