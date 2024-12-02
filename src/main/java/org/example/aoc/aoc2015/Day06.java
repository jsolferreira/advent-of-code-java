package org.example.aoc.aoc2015;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

class Day06 extends AoC2015Day<List<Day06.Instruction>> {

    private enum Action {
        TURN_OFF,
        TURN_ON,
        TOGGLE
    }

    private record Position(int i, int j) {
    }

    protected record Instruction(Action action, Position from, Position to) {
    }

    @Override
    protected List<Instruction> parseInput(String strInput) {

        final Pattern pattern = Pattern.compile("(.*) (\\d+),(\\d+) .* (\\d+),(\\d+)");

        return strInput.lines()
                .map(line -> {

                    final Matcher matcher = pattern.matcher(line);

                    if (matcher.find()) {

                        final Action action = switch (matcher.group(1)) {
                            case "turn off" -> Action.TURN_OFF;
                            case "turn on" -> Action.TURN_ON;
                            case "toggle" -> Action.TOGGLE;
                            default -> throw new RuntimeException();
                        };

                        final int from1 = Integer.parseInt(matcher.group(2));
                        final int from2 = Integer.parseInt(matcher.group(3));

                        final int to1 = Integer.parseInt(matcher.group(4));
                        final int to2 = Integer.parseInt(matcher.group(5));

                        return new Instruction(action, new Position(from1, from2), new Position(to1, to2));
                    }

                    throw new RuntimeException();
                })
                .toList();
    }

    @Override
    protected Long partOne(List<Instruction> input) {

        final boolean[][] grid = new boolean[1000][1000];

        for (Instruction instruction : input) {

            IntStream.rangeClosed(instruction.from.i, instruction.to.i)
                    .forEach(i -> IntStream.rangeClosed(instruction.from.j, instruction.to.j)
                            .forEach(j -> grid[i][j] = switch (instruction.action) {
                                case TURN_ON -> true;
                                case TURN_OFF -> false;
                                case TOGGLE -> !grid[i][j];
                            }));
        }

        return Arrays.stream(grid)
                .map(line -> IntStream.range(0, line.length)
                        .filter(i -> line[i])
                        .count())
                .reduce(0L, Long::sum);
    }

    @Override
    protected Integer partTwo(List<Instruction> input) {

        final int[][] grid = new int[1000][1000];

        for (Instruction instruction : input) {

            IntStream.rangeClosed(instruction.from.i, instruction.to.i)
                    .forEach(i -> IntStream.rangeClosed(instruction.from.j, instruction.to.j)
                            .forEach(j -> grid[i][j] = switch (instruction.action) {
                                case TURN_ON -> grid[i][j] + 1;
                                case TURN_OFF -> Math.max(grid[i][j] - 1, 0);
                                case TOGGLE -> grid[i][j] + 2;
                            }));
        }

        return Arrays.stream(grid)
                .map(line -> Arrays.stream(line).reduce(0, Integer::sum))
                .reduce(0, Integer::sum);
    }

    @Override
    protected String getDay() {

        return "day06";
    }
}
