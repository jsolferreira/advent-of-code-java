package org.example.aoc.aoc2022;

import java.util.List;
import java.util.Map;

class Day02 extends AoC2022Day<List<Day02.Round>> {

    private enum Shape {
        ROCK,
        PAPER,
        SCISSORS
    }

    private enum ExpectedOutcome {
        DRAW,
        WIN,
        LOSE
    }

    protected record Round(Shape a, Shape b, ExpectedOutcome expectedOutcome) {
    }

    private record Result(Shape wins, Shape loses) {
    }

    private final Map<Shape, Result> resultsMap = Map.of(
            Shape.ROCK, new Result(Shape.SCISSORS, Shape.PAPER),
            Shape.SCISSORS, new Result(Shape.PAPER, Shape.ROCK),
            Shape.PAPER, new Result(Shape.ROCK, Shape.SCISSORS)
    );

    private final Map<Shape, Integer> pointsMap = Map.of(
            Shape.ROCK, 1,
            Shape.PAPER, 2,
            Shape.SCISSORS, 3
    );

    @Override
    protected List<Round> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> {
                    final String[] split = line.split(" ");

                    return new Round(getShape(split[0]), getShape(split[1]), getExpectedOutcome(split[1]));
                })
                .toList();
    }

    @Override
    protected Long partOne(List<Round> input) {

        return input.stream()
                .mapToLong(round -> {

                    if (round.b.equals(round.a)) {

                        return 3 + pointsMap.get(round.b);
                    } else if (resultsMap.get(round.b).wins.equals(round.a)) {


                        return 6 + pointsMap.get(round.b);
                    } else {

                        return pointsMap.get(round.b);
                    }
                })
                .sum();
    }

    private Shape getShape(String s) {

        return switch (s) {
            case "A", "X" -> Shape.ROCK;
            case "B", "Y" -> Shape.PAPER;
            case "C", "Z" -> Shape.SCISSORS;
            default -> throw new RuntimeException();
        };
    }

    private ExpectedOutcome getExpectedOutcome(String s) {

        return switch (s) {
            case "X" -> ExpectedOutcome.LOSE;
            case "Y" -> ExpectedOutcome.DRAW;
            case "Z" -> ExpectedOutcome.WIN;
            default -> throw new RuntimeException();
        };
    }

    @Override
    protected Long partTwo(List<Round> input) {

        return input.stream()
                .mapToLong(round -> switch (round.expectedOutcome) {
                    case DRAW -> 3 + pointsMap.get(round.a);
                    case WIN -> 6 + pointsMap.get(resultsMap.get(round.a).loses);
                    case LOSE -> pointsMap.get(resultsMap.get(round.a).wins);
                })
                .sum();
    }

    @Override
    protected String getDay() {

        return "day02";
    }
}
