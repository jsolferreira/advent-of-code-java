package org.example.aoc.aoc2020;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Day15 extends AoC2020Day<List<Integer>> {

    @Override
    protected List<Integer> parseInput(String strInput) {

        return Arrays.stream(strInput.split(","))
                .map(Integer::parseInt)
                .toList();
    }

    @Override
    protected Long partOne(List<Integer> input) {

        return run(input, 2020);
    }

    @Override
    protected Long partTwo(List<Integer> input) {

        return run(input, 30000000);
    }

    private long run(List<Integer> input, int numberSpoken) {

        final Map<Integer, Integer> saidNumbers = new HashMap<>();

        for (int i = 0; i < input.size() - 1; i++) {
            saidNumbers.put(input.get(i), i + 1);
        }

        int lastNumber = input.get(input.size() - 1);

        for (int i = input.size(); i < numberSpoken; i++) {

            if (saidNumbers.containsKey(lastNumber)) {

                final int previousPosition = saidNumbers.get(lastNumber);
                saidNumbers.put(lastNumber, i);
                lastNumber = i - previousPosition;
            } else {

                saidNumbers.put(lastNumber, i);
                lastNumber = 0;
            }
        }

        return lastNumber;
    }

    @Override
    protected String getDay() {

        return "day15";
    }
}
