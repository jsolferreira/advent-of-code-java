package org.example.aoc.aoc2021;

import java.util.List;

class Day02 extends AoC2021Day<List<Day02.Step>> {

    private enum Command {
        FORWARD,
        DOWN,
        UP
    }

    protected record Step(Command command, int units) {}

    protected record Pos(int position, int depth, int aim) {

        private Pos(int position, int depth) {

            this(position, depth, 0);
        }
    }

    @Override
    protected List<Step> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> {
                    final String[] split = line.split(" ");

                    final Command command = switch (split[0]) {
                        case "forward" -> Command.FORWARD;
                        case "down" -> Command.DOWN;
                        case "up" -> Command.UP;
                        default -> throw new RuntimeException();
                    };

                    final int units = Integer.parseInt(split[1]);

                    return new Step(command, units);
                })
                .toList();
    }

    @Override
    protected Long partOne(List<Step> input) {

        final Pos finalPosition = input.stream()
                .reduce(new Pos(0, 0),
                        (acc, val) -> switch (val.command) {
                            case FORWARD -> new Pos(acc.position + val.units, acc.depth);
                            case DOWN -> new Pos(acc.position, acc.depth + val.units);
                            case UP -> new Pos(acc.position, acc.depth - val.units);
                        },
                        (a, b) -> a);

        return (long) finalPosition.depth * finalPosition.position;
    }

    @Override
    protected Long partTwo(List<Step> input) {

        final Pos finalPosition = input.stream()
                .reduce(new Pos(0, 0, 0),
                        (acc, val) -> switch (val.command) {
                            case FORWARD -> new Pos(acc.position + val.units, acc.depth + acc.aim * val.units, acc.aim);
                            case DOWN -> new Pos(acc.position, acc.depth, acc.aim + val.units);
                            case UP -> new Pos(acc.position, acc.depth, acc.aim - val.units);
                        },
                        (a, b) -> a);

        return (long) finalPosition.depth * finalPosition.position;
    }

    @Override
    protected String getDay() {

        return "day02";
    }
}
