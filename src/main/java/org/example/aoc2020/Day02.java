package org.example.aoc2020;

import java.util.List;

class Day01 extends AbstractAoC2020<Integer, List<Integer>> {

    @Override
    protected List<Integer> parseInput(String strInput) {

        return strInput.lines()
                .map(Integer::parseInt)
                .toList();
    }

    @Override
    protected Integer partOne(List<Integer> input) {

        for (int i = 0; i < input.size(); i++) {

            final int a = input.get(i);

            for (int j = i + 1; j < input.size(); j++) {

                final int b = input.get(j);

                if (a + b == 2020) {

                    return a * b;
                }
            }
        }

        return null;
    }

    @Override
    protected Integer partTwo(List<Integer> input) {

        for (int i = 0; i < input.size(); i++) {

            final int a = input.get(i);

            for (int j = i + 1; j < input.size(); j++) {

                final int b = input.get(j);

                if (a + b >= 2020) continue;

                for (int k = j + 1; k < input.size(); k++) {

                    final int c = input.get(k);

                    if (a + b + c == 2020) {

                        return a * b * c;
                    }
                }
            }
        }

        return null;
    }

    @Override
    protected String getDay() {

        return "day01";
    }
}
