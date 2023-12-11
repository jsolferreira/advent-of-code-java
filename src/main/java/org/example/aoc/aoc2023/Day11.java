package org.example.aoc.aoc2023;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

class Day11 extends AoC2023Day<char[][]> {

    private record Expandable(List<Integer> rows, List<Integer> columns) {}

    private record Position(int i, int j) {
    }

    @Override
    protected char[][] parseInput(String strInput) {

        return strInput.lines()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    @Override
    protected Long partOne(char[][] input) {

        return getDistance(input, 2);
    }

    @Override
    protected Long partTwo(char[][] input) {

        return getDistance(input, 1000000);
    }

    private Long getDistance(char[][] input, int toExpand) {

        final Expandable expandable = getExpandableColumnsAndRows(input);

        final List<Position> galaxies = IntStream.range(0, input.length)
                .boxed()
                .flatMap(i -> IntStream.range(0, input[i].length)
                        .filter(j -> input[i][j] == '#')
                        .mapToObj(j -> {
                            final long expandedRows = expandable.rows.stream().filter(c -> i > c).count() * (toExpand - 1);
                            final long expandedColumns = expandable.columns.stream().filter(c -> j > c).count() * (toExpand - 1);
                            return new Position((int) (i + expandedRows), (int) (j + expandedColumns));
                        }))
                .toList();

        long a = 0;

        for (int i = 0; i < galaxies.size() - 1; i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                final Position first = galaxies.get(i);
                final Position second = galaxies.get(j);

                a += Math.abs(first.i - second.i) + Math.abs(first.j - second.j);
            }
        }

        return a;
    }

    private Expandable getExpandableColumnsAndRows(char[][] input) {

        final List<Integer> rows = new ArrayList<>();
        final List<Integer> columns = new ArrayList<>();

        for (int i = 0, j = 0; i < input.length || j < input[0].length; i++, j++) {

            boolean allMatch = true;

            for (int jj = 0; jj < input[i].length; jj++) {
                allMatch = allMatch && input[i][jj] == '.';
            }

            if (allMatch) {
                rows.add(i);
            }

            allMatch = true;

            for (char[] chars : input) {
                allMatch = allMatch && chars[j] == '.';
            }

            if (allMatch) {
                columns.add(i);
            }
        }

        return new Expandable(rows, columns);
    }

    @Override
    protected String getDay() {

        return "day11";
    }
}
