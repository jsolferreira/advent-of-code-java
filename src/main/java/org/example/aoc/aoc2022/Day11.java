package org.example.aoc.aoc2022;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Day11 extends AoC2022Day<List<Day11.Monkey>> {

    protected record Monkey(int id,
                            Queue<Long> itemWorryLevels,
                            LongUnaryOperator operation,
                            int test,
                            int ifTrueThrowTo,
                            int ifFalseThrowTo) {

        @Override
        public int hashCode() {

            return id;
        }
    }

    @Override
    protected List<Monkey> parseInput(String strInput) {

        return Arrays.stream(strInput.split(System.lineSeparator() + System.lineSeparator()))
                .map(monkeyInput -> {

                    final int monkeyId = getMonkeyId(monkeyInput);
                    final Queue<Long> startingItems = getStartingItems(monkeyInput);
                    final LongUnaryOperator operation = getOperation(monkeyInput);
                    final int test = getTest(monkeyInput);
                    final int ifTrueThrowTo = getIfTrueThrowTo(monkeyInput);
                    final int ifFalseThrowTo = getIfFalseThrowTo(monkeyInput);

                    return new Monkey(monkeyId, startingItems, operation, test, ifTrueThrowTo, ifFalseThrowTo);
                })
                .toList();
    }

    private int getMonkeyId(String monkeyInput) {

        final Pattern pattern = Pattern.compile("Monkey (\\d+)");
        final Matcher matcher = pattern.matcher(monkeyInput);

        if (matcher.find()) {

            return Integer.parseInt(matcher.group(1));
        }

        throw new RuntimeException();
    }

    private Queue<Long> getStartingItems(String monkeyInput) {

        final Pattern pattern = Pattern.compile("Starting items: (.*)");
        final Matcher matcher = pattern.matcher(monkeyInput);

        if (matcher.find()) {

            return Arrays.stream(matcher.group(1).split(", "))
                    .map(Long::parseLong)
                    .collect(Collectors.toCollection(LinkedList::new));
        }

        throw new RuntimeException();
    }

    private LongUnaryOperator getOperation(String monkeyInput) {

        final Pattern pattern = Pattern.compile("Operation: new = (.*) ([*+]) (.*)");
        final Matcher matcher = pattern.matcher(monkeyInput);

        if (matcher.find()) {

            final String op1 = matcher.group(1);
            final LongBinaryOperator operator = matcher.group(2).equals("+") ? Long::sum : (a, b) -> a * b;
            final String op2 = matcher.group(3);

            if (op1.equals("old")) {

                if (op2.equals("old")) {

                    return x -> operator.applyAsLong(x, x);
                } else {

                    return x -> operator.applyAsLong(x, Integer.parseInt(op2));
                }
            } else if (op2.equals("old")) {

                return x -> operator.applyAsLong(Integer.parseInt(op1), x);
            } else {

                return x -> operator.applyAsLong(Integer.parseInt(op1), Integer.parseInt(op2));
            }
        }

        throw new RuntimeException();
    }

    private int getTest(String monkeyInput) {

        final Pattern pattern = Pattern.compile("divisible by (\\d+)");
        final Matcher matcher = pattern.matcher(monkeyInput);

        if (matcher.find()) {

            return Integer.parseInt(matcher.group(1));
        }

        throw new RuntimeException();
    }

    private int getIfTrueThrowTo(String monkeyInput) {

        final Pattern pattern = Pattern.compile("If true: .* (\\d+)");
        final Matcher matcher = pattern.matcher(monkeyInput);

        if (matcher.find()) {

            return Integer.parseInt(matcher.group(1));
        }

        throw new RuntimeException();
    }

    private int getIfFalseThrowTo(String monkeyInput) {

        final Pattern pattern = Pattern.compile("If false: .* (\\d+)");
        final Matcher matcher = pattern.matcher(monkeyInput);

        if (matcher.find()) {

            return Integer.parseInt(matcher.group(1));
        }

        throw new RuntimeException();
    }

    @Override
    protected Long partOne(List<Monkey> input) {

        final Map<Monkey, Long> inspectedItems = new HashMap<>();

        for (int i = 0; i < 20; i++) {

            for (Monkey monkey : input) {

                while (!monkey.itemWorryLevels.isEmpty()) {

                    long itemWorryLevel = monkey.itemWorryLevels.remove();

                    itemWorryLevel = monkey.operation.applyAsLong(itemWorryLevel);

                    itemWorryLevel = itemWorryLevel / 3;

                    final int monkeyToThrowTo = itemWorryLevel % monkey.test == 0 ? monkey.ifTrueThrowTo : monkey.ifFalseThrowTo;

                    input.get(monkeyToThrowTo).itemWorryLevels.add(itemWorryLevel);

                    inspectedItems.compute(monkey, (k, v) -> v == null ? 1 : v + 1);
                }
            }
        }

        return inspectedItems.values().stream()
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .reduce(1L, (a, b) -> a * b);
    }

    @Override
    protected Long partTwo(List<Monkey> input) {

        return null;
    }

    @Override
    protected String getDay() {

        return "day11";
    }
}
