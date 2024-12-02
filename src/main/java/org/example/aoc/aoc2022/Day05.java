package org.example.aoc.aoc2022;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day05 extends AoC2022Day<Day05.Input> {

    protected record Input(List<Stack<String>> stacks, List<Procedure> procedures) {
    }

    protected record Procedure(int move, int from, int to) {
    }

    @Override
    protected Input parseInput(String strInput) {

        final String[] split = strInput.split(" \\d.*" + System.lineSeparator() + System.lineSeparator());

        return new Input(parseStacks(split[0]), parseProcedures(split[1]));
    }

    private List<Stack<String>> parseStacks(String stackPart) {

        final Pattern stacksPattern = Pattern.compile("\\[(\\w)]|\\s{3}[ |\\n]");

        final List<Stack<String>> stacks = new ArrayList<>();

        final String[] stackString = stackPart.split("\n");

        for (int line = stackString.length - 1; line >= 0; line--) {

            final Matcher matcher = stacksPattern.matcher(stackString[line]);

            for (int i = 0; matcher.find(); i++) {

                if (i >= stacks.size()) {

                    stacks.add(new Stack<>());
                }

                final Stack<String> stack = stacks.get(i);

                if (matcher.group(1) != null) {

                    stack.add(matcher.group(1));
                }
            }
        }

        return stacks;
    }

    private List<Procedure> parseProcedures(String procedures) {

        final Pattern procedurePattern = Pattern.compile("(\\d+).*(\\d+).*(\\d+)");

        return procedures.lines()
                .map(procedurePattern::matcher)
                .filter(Matcher::find)
                .map(matcher -> new Procedure(Integer.parseInt(matcher.group(1)),
                                              Integer.parseInt(matcher.group(2)),
                                              Integer.parseInt(matcher.group(3))))
                .toList();
    }

    @Override
    protected String partOne(Input input) {

        final List<Stack<String>> stacks = copyStacks(input.stacks);

        input.procedures.forEach(procedure -> move(stacks.get(procedure.from - 1),
                                                   stacks.get(procedure.to - 1),
                                                   procedure.move));

        return joinTopOfStacks(stacks);
    }

    @Override
    protected String partTwo(Input input) {

        final List<Stack<String>> stacks = copyStacks(input.stacks);

        input.procedures.forEach(procedure -> {

            final Stack<String> auxStack = new Stack<>();

            move(stacks.get(procedure.from - 1), auxStack, procedure.move);
            move(auxStack, stacks.get(procedure.to - 1), auxStack.size());
        });

        return joinTopOfStacks(stacks);
    }

    private void move(Stack<String> from, Stack<String> to, int n) {

        IntStream.iterate(n, i -> i > 0, i -> i - 1)
                .forEach(_ -> to.add(from.pop()));
    }

    private String joinTopOfStacks(List<Stack<String>> stacks) {

        return stacks.stream()
                .map(Stack::peek)
                .collect(Collectors.joining());
    }

    private List<Stack<String>> copyStacks(List<Stack<String>> stacks) {

        return stacks.stream()
                .map(stack -> {
                    Stack<String> objects = new Stack<>();
                    objects.addAll(stack);

                    return objects;
                })
                .toList();
    }

    @Override
    protected String getDay() {

        return "day05";
    }
}
