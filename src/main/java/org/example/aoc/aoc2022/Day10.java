package org.example.aoc.aoc2022;

import java.util.List;

class Day10 extends AoC2022Day<List<Day10.Instruction>> {

    private enum InstructionName {
        NOOP,
        ADDX
    }

    protected record Instruction(InstructionName name, int value) {
    }

    @Override
    protected List<Instruction> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> {
                    final String[] split = line.split(" ");

                    final InstructionName instructionName = switch (split[0]) {
                        case "noop" -> InstructionName.NOOP;
                        case "addx" -> InstructionName.ADDX;
                        default -> throw new RuntimeException();
                    };

                    final int steps = split.length == 1 ? 0 : Integer.parseInt(split[1]);

                    return new Instruction(instructionName, steps);
                })
                .toList();
    }

    @Override
    protected Integer partOne(List<Instruction> input) {

        int x = 1;
        int sum = 0;
        int i = 0;
        int cycle = 1;
        boolean inCycle = false;

        while (i < input.size()) {

            if (cycle % 40 == 20) {
                sum += cycle * x;
            }

            final Instruction instruction = input.get(i);

            if (instruction.name.equals(InstructionName.ADDX)) {

                if (inCycle) {

                    x += instruction.value;
                    i++;
                }

                inCycle = !inCycle;
            } else {
                i++;
            }
            cycle++;
        }

        return sum;
    }

    @Override
    protected Integer partTwo(List<Instruction> input) {

        int spritePosition = 1;
        final char[][] screen = new char[6][40];
        int i = 0;
        int cycle = 0;
        boolean inCycle = false;

        while (i < input.size()) {

            drawPixel(screen, cycle, spritePosition);

            final Instruction instruction = input.get(i);

            if (instruction.name.equals(InstructionName.ADDX)) {

                if (inCycle) {

                    spritePosition += instruction.value;
                    i++;
                }

                inCycle = !inCycle;
            } else {
                i++;
            }

            cycle++;
        }

        printCRT(screen);

        return 0;
    }

    private void drawPixel(char[][] screen, int cycle, int spritePosition) {

        if (isSpritePositionedInPixelBeingDrawn(spritePosition, cycle % 40)) {

            screen[cycle / 40][cycle % 40] = '#';
        } else {

            screen[cycle / 40][cycle % 40] = '.';
        }
    }

    private boolean isSpritePositionedInPixelBeingDrawn(int spritePosition, int cycle) {

        return spritePosition - 1 <= cycle && cycle <= spritePosition + 1;
    }

    private void printCRT(char[][] screen) {

        for (char[] chars : screen) {
            StringBuilder sb = new StringBuilder();

            for (char aChar : chars) {

                sb.append(aChar);
            }

            System.out.println(sb);
        }
    }

    @Override

    protected String getDay() {

        return "day10";
    }
}
