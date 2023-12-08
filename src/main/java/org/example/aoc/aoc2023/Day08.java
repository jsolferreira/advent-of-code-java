package org.example.aoc.aoc2023;

import org.example.aoc.utils.Utils;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Day08 extends AoC2023Day<Day08.PuzzleMap> {

    private enum Instruction {
        L,
        R
    }

    protected record PuzzleMap(List<Instruction> instructions, java.util.Map<String, String[]> mapping) {}

    @Override
    protected PuzzleMap parseInput(String strInput) {

        final Pattern pattern = Pattern.compile("(\\w+) = \\((\\w+), (\\w+)\\)");

        final String[] split = strInput.split(System.lineSeparator() + System.lineSeparator());

        final List<Instruction> instructions = split[0].chars().mapToObj(c -> c == 'R' ? Instruction.R : Instruction.L).toList();
        final Map<String, String[]> mapping = split[1].lines()
                .map(l -> {
                    final Matcher matcher = pattern.matcher(l);

                    if (matcher.matches()) {

                        return Map.entry(matcher.group(1), new String[]{matcher.group(2), matcher.group(3)});
                    }

                    return Map.entry("", new String[]{});
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new PuzzleMap(instructions, mapping);
    }

    @Override
    protected Long partOne(PuzzleMap input) {

        return getFirstNode(input.instructions, input.mapping, "AAA", node -> node.equals("ZZZ"));
    }

    @Override
    protected BigInteger partTwo(PuzzleMap input) {

        final List<String> startingNodes = input.mapping.keySet().stream().filter(node -> node.endsWith("A")).toList();

        List<Long> numbers = startingNodes
                .stream()
                .map(startingNode -> getFirstNode(input.instructions, input.mapping, startingNode, node -> node.endsWith("Z")))
                .toList();

        BigInteger result = BigInteger.ONE;
        boolean continueLooping = true;
        int i = 0;

        while (continueLooping) {

            final int primeNumber = Utils.primeNumbers.get(i);

            boolean anyNumberDivisible = numbers.stream().anyMatch(x -> x % primeNumber == 0);

            if (anyNumberDivisible) {
                result = result.multiply(BigInteger.valueOf(primeNumber));

                numbers = numbers.stream().map(x -> x % primeNumber == 0 ? x / primeNumber : x).toList();
                i = 0;
            }

            continueLooping = !numbers.stream().allMatch(x -> x == 1);
            i++;
        }

        return result;
    }

    private long getFirstNode(List<Instruction> instructions,
                              Map<String, String[]> mapping,
                              String currentNode,
                              Predicate<String> condition) {

        long i = 0;

        while (condition.negate().test(currentNode)) {

            currentNode = getNextNode(instructions, mapping, i, currentNode);

            i++;
        }

        return i;
    }

    private String getNextNode(List<Instruction> instructions, Map<String, String[]> mapping, long currentInstruction, String currentNode) {

        final String[] possibleNextNodes = mapping.get(currentNode);

        final Instruction nextInstruction = instructions.get((int) currentInstruction % instructions.size());

        return nextInstruction.equals(Instruction.L) ? possibleNextNodes[0] : possibleNextNodes[1];
    }

    @Override
    protected String getDay() {

        return "day08";
    }
}
