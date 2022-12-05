package org.example.aoc.aoc2019;

import java.util.Arrays;
import java.util.function.LongBinaryOperator;
import java.util.stream.LongStream;

class Day02 extends AoC2019Day<Long[]> {

    @Override
    protected Long[] parseInput(String strInput) {

        return Arrays.stream(strInput.split(","))
                .map(Long::parseLong)
                .toArray(Long[]::new);
    }

    @Override
    protected Long partOne(Long[] input) {

        return run(input, 12, 2);
    }

    @Override
    protected Long partTwo(Long[] input) {

        final long desiredOutput = 19690720;

        return LongStream.range(0, 99)
                .flatMap(noun -> LongStream.range(0, 99)
                        .filter(verb -> run(input, noun, verb) == desiredOutput)
                        .map(verb -> 100 * noun + verb))
                .findFirst()
                .orElseThrow();
    }

    private long run(Long[] input, long noun, long verb) {

        final Long[] clonedInput = Arrays.stream(input).toArray(Long[]::new);

        clonedInput[1] = noun;
        clonedInput[2] = verb;

        for (int i = 0; i < clonedInput.length; i += 4) {

            final int opcode = clonedInput[i].intValue();

            if (opcode == 99) {

                break;
            }

            if (opcode == 1) {

                applyOpcode(clonedInput, i, this::opcode1);
            } else if (opcode == 2) {

                applyOpcode(clonedInput, i, this::opcode2);
            }
        }

        return clonedInput[0];
    }

    private void applyOpcode(Long[] input, int index, LongBinaryOperator opcodeOperator) {

        final int a = input[index + 1].intValue();
        final int b = input[index + 2].intValue();
        final int c = input[index + 3].intValue();

        final long val1 = input[a];
        final long val2 = input[b];

        input[c] = opcodeOperator.applyAsLong(val1, val2);
    }

    private long opcode1(long a, long b) {

        return a + b;
    }

    private long opcode2(long a, long b) {

        return a * b;
    }

    @Override
    protected String getDay() {

        return "day02";
    }
}
