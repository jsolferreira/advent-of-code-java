package org.example.aoc.aoc2022;

import org.example.base.AoCDay;

public abstract class AoC2022StringDay<T> extends AoCDay<T, String> {

    protected abstract T parseInput(String strInput);

    protected abstract String partOne(T input);

    protected abstract String partTwo(T input);

    @Override
    protected String getYear() {

        return "aoc2022";
    }
}
