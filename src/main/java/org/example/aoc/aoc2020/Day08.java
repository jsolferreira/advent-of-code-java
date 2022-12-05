package org.example.aoc.aoc2020;

import java.util.HashSet;
import java.util.List;

class Day08 extends AoC2020Day<List<Day08.Instruction>> {

    private enum Operation {
        NOP,
        JMP,
        ACC
    }

    protected record Instruction(Operation op, int argument) {
    }

    private static class LoopException extends Exception {

        private final int accumulator;

        public LoopException(int accumulator) {

            this.accumulator = accumulator;
        }

        public long getAccumulator() {

            return this.accumulator;
        }
    }

    @Override
    protected List<Instruction> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> {
                    final String[] split = line.split(" ");

                    return new Instruction(Operation.valueOf(split[0].toUpperCase()), Integer.parseInt(split[1]));
                })
                .toList();
    }

    @Override
    protected Long partOne(List<Instruction> input) {

        try {
            return run(input, 0);
        } catch (LoopException e) {
            return e.getAccumulator();
        }
    }

    @Override
    protected Long partTwo(List<Instruction> input) {

        long accumulator = 0;

        for (int i = 0; i < input.size(); ) {

            final Instruction instruction = input.get(i);

            if (instruction.op != Operation.ACC) {
                final int nextInstruction = instruction.op == Operation.NOP ? i + 1 : i + instruction.argument;
                final int nextInstructionIfError = instruction.op == Operation.NOP ? i + instruction.argument : i + 1;

                try {
                    return accumulator + run(input, nextInstruction);
                } catch (LoopException e) {
                    try {
                        return accumulator + run(input, nextInstructionIfError);
                    } catch (LoopException ignore) {
                    }
                }
            }

            switch (instruction.op) {
                case NOP -> i++;
                case JMP -> i += instruction.argument;
                case ACC -> {
                    i++;
                    accumulator += instruction.argument;
                }
            }
        }

        return accumulator;
    }

    private long run(List<Instruction> input, int i) throws LoopException {

        int accumulator = 0;
        final HashSet<Integer> visitedInstructions = new HashSet<>();

        while (i < input.size()) {

            if (visitedInstructions.contains(i)) {

                throw new LoopException(accumulator);
            }

            visitedInstructions.add(i);

            final Instruction instruction = input.get(i);

            switch (instruction.op) {
                case NOP -> i++;
                case JMP -> i += instruction.argument;
                case ACC -> {
                    i++;
                    accumulator += instruction.argument;
                }
            }
        }

        return accumulator;
    }

    @Override
    protected String getDay() {

        return "day08";
    }
}
