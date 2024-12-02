package org.example.aoc.aoc2020;

import java.util.List;
import java.util.function.LongBinaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day18 extends AoC2020Day<List<String>> {

    @Override
    protected List<String> parseInput(String strInput) {

        return strInput.lines().toList();
    }

    @Override
    protected Long partOne(List<String> input) {
        final Pattern pattern = Pattern.compile("\\(([^(]*?)\\)");

        return input.stream()
                .map(expression -> {
                    Matcher matcher = pattern.matcher(expression);
                    while (matcher.find()) {

                        final String group = matcher.group(1);

                        long l = resolveSimpleExpression(group);
                        expression = matcher.replaceFirst(String.valueOf(l));
                        matcher = pattern.matcher(expression);
                    }

                    return resolveSimpleExpression(expression);
                })
                .reduce(0L, Long::sum);
    }

    private long resolveSimpleExpression(String expression) {

        final Pattern pattern = Pattern.compile("(\\d+) ([+*]) (\\d+)");

        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()) {

            final long a = Long.parseLong(matcher.group(1));
            final String op = matcher.group(2);
            final long b = Long.parseLong(matcher.group(3));

            final LongBinaryOperator operator = op.equals("+") ? Long::sum : (x, y) -> x * y;

            expression = matcher.replaceFirst(String.valueOf(operator.applyAsLong(a, b)));
            matcher = pattern.matcher(expression);
        }

        return Long.parseLong(expression);
    }

    @Override
    protected Long partTwo(List<String> input) {
        final Pattern pattern = Pattern.compile("\\(([^(]*?)\\)");

        return input.stream()
                .map(expression -> {
                    Matcher matcher = pattern.matcher(expression);
                    while (matcher.find()) {

                        final String group = matcher.group(1);

                        long l = resolveSum(group);
                        expression = matcher.replaceFirst(String.valueOf(l));
                        matcher = pattern.matcher(expression);
                    }

                    return resolveSum(expression);
                })
                .reduce(0L, Long::sum);
    }

    private long resolveSum(String expression) {

        final String unfoldedExpression = unfold(expression, '+', Long::sum);

        return resolveMultiply(unfoldedExpression);
    }

    private long resolveMultiply(String expression) {

        final String unfoldedExpression = unfold(expression, '*', (a, b) -> a * b);

        return Long.parseLong(unfoldedExpression);
    }

    private String unfold(String expression, Character operatorSymbol, LongBinaryOperator operator) {

        final String operatorRegex = operatorSymbol == '+' ? "\\+" : "\\*";
        final String regex = "(\\d+) " + operatorRegex + " (\\d+)";
        final Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()) {

            final long a = Long.parseLong(matcher.group(1));
            final long b = Long.parseLong(matcher.group(2));

            expression = matcher.replaceFirst(String.valueOf(operator.applyAsLong(a, b)));
            matcher = pattern.matcher(expression);
        }

        return expression;
    }

    @Override
    protected String getDay() {

        return "day18";
    }
}
