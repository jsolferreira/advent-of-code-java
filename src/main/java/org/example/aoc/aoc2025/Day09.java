package org.example.aoc.aoc2025;

import java.util.ArrayList;
import java.util.List;

class Day09 extends AoC2025Day<List<Day09.Coords>> {

    protected record Coords(int x, int y) {}

    @Override
    protected List<Coords> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> line.split(","))
                .map(split -> new Coords(Integer.parseInt(split[0]), Integer.parseInt(split[1])))
                .toList();
    }

    @Override
    protected Long partOne(List<Coords> input) {

        int maxX = Integer.MIN_VALUE;
        int minX = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;

        final List<Coords> coords = new ArrayList<>();

        for (Coords coord : input) {

            boolean add = false;

            if (coord.x() >= maxX) {
                maxX = coord.x();
                add = true;
            }
            if (coord.x() <= minX) {
                minX = coord.x();
                add = true;
            }
            if (coord.y() >= maxY) {
                maxY = coord.y();
                add = true;
            }
            if (coord.y() <= minY) {
                minY = coord.y();
                add = true;
            }

            if (add) {
                coords.add(coord);
            }
        }

        long maxArea = Integer.MIN_VALUE;

        for (int i = 0; i < coords.size() - 1; i++) {
            for (int j = i + 1; j < coords.size(); j++) {
                final Coords coords1 = coords.get(i);
                final Coords coords2 = coords.get(j);
                final long area = (long) (Math.abs(coords1.x() - coords2.x()) + 1) * (Math.abs(coords1.y() - coords2.y()) + 1);
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }

        return maxArea;
    }

    @Override
    protected Integer partTwo(List<Coords> input) {

        return null;
    }

    @Override
    protected String getDay() {

        return "day09";
    }
}
