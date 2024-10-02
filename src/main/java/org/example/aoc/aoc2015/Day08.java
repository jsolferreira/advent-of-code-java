package org.example.aoc.aoc2015;

import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Pattern;

class Day08 extends AoC2015Day<List<Day08.Instruction>> {

    protected record Instruction(Supplier<Integer> operation, String op1, String op2 , String output) {
    }

    @Override
    protected List<Instruction> parseInput(String strInput) {

        final Pattern assignementPattern = Pattern.compile("(\\d+) -> (\\w)");
        final Pattern andPattern = Pattern.compile("(.+) AND (.+) -> (\\w)");
        final Pattern orPattern = Pattern.compile("(.+) OR (.+) -> (\\w)");
        final Pattern lshiftPattern = Pattern.compile("(.+) LSHIFT (.+) -> (\\w)");
        final Pattern rshiftPattern = Pattern.compile("(.+) RSHIFT (.+) -> (\\w)");
        final Pattern notPattern = Pattern.compile("NOT (.+) -> (\\w)");

        return null;
    }

    @Override
    protected Long partOne(List<Instruction> input) {

        return null;
    }

    @Override
    protected Integer partTwo(List<Instruction> input) {

        return null;
    }

    @Override
    protected String getDay() {

        return "day08";
    }
}
