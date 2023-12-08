package org.example.aoc.aoc2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    protected Integer partOne(PuzzleMap input) {

        String currentNode = "AAA";
        int i = 0;

        /*while (!currentNode.equals("ZZZ")) {

            currentNode = getNextNode(input.instructions, input.mapping, i, currentNode);

            i++;
        }*/

        return i;
    }

    @Override
    protected Integer partTwo(PuzzleMap input) {

        List<String> nodes = input.mapping.keySet().stream().filter(node -> node.endsWith("A")).toList();
        List<Integer> list = nodes.stream().map(x -> x(input.instructions, input.mapping, x)).toList();

        int i = 0;

        for (int j = 1, k = 1; j < 10; j++, k++) {
            //for (int k = 1; k < 10; k++) {
            int r1 = j + (j - 1);
            int r2 = 2 * k + (k - 1);
            if (2 * j + 2 - 3 * k - 3 == 0) {
                System.out.println();
            }
            //}
        }

        for (int j = 1; j < 100000000; j++) {
            if ((61f * j) % 73f == 0 && (61f * j) % 79f == 0 && (61f * j) % 53f == 0 && (61f * j) % 47f == 0 && (61f * j) % 59f == 0) {
                System.out.println();
            }
        }

        for (int k1 = 1; k1 < 100; k1++) {
            for (int k2 = 1; k2 < 100; k2++) {
               /* for (int k3 = 1; k3 < 100; k3++) {
                    for (int k4 = 1; k4 < 100; k4++) {
                        for (int k5 = 1; k5 < 100; k5++) {
                            for (int k6 = 1; k6 < 100; k6++) {
                                if (13019*k1 - 14681*k2 == 0 && 16343*k3 - 16897*k4 == 0 &&20221*k5 - 21883*k6 == 0) {
                                    System.out.println();
                                }
                            }
                        }
                    }
                }*/
                if (13019 * k1 - 14681 * k2 == 0) {
                    System.out.println();
                }
                if (16343 * k1 - 16897 * k2 == 0) {
                    System.out.println();
                }
                if (20221 * k1 - 21883 * k2 == 0) {
                    System.out.println();
                }
            }
        }

        for (int j = 1; j < 1000000; j++) {
            if (53*j == 47*j) {
                System.out.println();
            }
        }

        /*k3 = 61
k4 = 59
k5 = 79
k6 = 73*/

        for (int k1 = 53, k2 = 47; k1 < 1000000000; k1 += 47, k2 += 53) {
            for (int k3 = 61, k4 = 59; k3 < 1000000000; k3 += 61, k4 += 59) {
                for (int k5 = 79, k6 = 73; k5 < 1000000000; k5 += 79, k6 += 73) {
                    if (13019 * k1 - 14681 * k2 == 0 && 16343 * k3 - 16897 * k4 == 0 && 20221 * k5 - 21883 * k6 == 0) {
                        System.out.println();
                    }
                }
            }
        }
        // 47, 53
        // 61, 59
        // 79, 73

        Map<String, List<Integer>> map = new HashMap<>();

        while (!allNodesEndWithZ(nodes)) {

            final int currentInstruction = i;
            nodes = nodes.stream().map(node -> getNextNode(input.instructions, input.mapping, currentInstruction, node)).toList();

            List<String> z = nodes.stream().filter(x -> x.endsWith("Z")).toList();

            if (!z.isEmpty()) {

                for (String s : z) {

                    map.putIfAbsent(s, new ArrayList<>());
                    map.merge(s, List.of(i + 1), (a, b) -> Stream.concat(a.stream(), b.stream()).toList());
                }

                System.out.println(map.toString());
                i = i;
            }

            i++;
        }

        return i;
    }

    private boolean allNodesEndWithZ(List<String> nodes) {

        return nodes.stream().allMatch(node -> node.endsWith("Z"));
    }

    private int x(List<Instruction> instructions, Map<String, String[]> mapping, String currentNode) {

        int i = 0;

        while (!currentNode.endsWith("Z")) {

            currentNode = getNextNode(instructions, mapping, i, currentNode);

            i++;
        }

        return i;
    }

    private String getNextNode(List<Instruction> instructions, Map<String, String[]> mapping, int currentInstruction, String currentNode) {

        final String[] possibleNextNodes = mapping.get(currentNode);

        final Instruction nextInstruction = instructions.get(currentInstruction % instructions.size());

        return nextInstruction.equals(Instruction.L) ? possibleNextNodes[0] : possibleNextNodes[1];
    }

    @Override
    protected String getDay() {

        return "day08";
    }
}
