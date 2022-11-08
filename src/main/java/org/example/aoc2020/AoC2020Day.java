package org.example.aoc2020;

import org.example.base.AoCDay;

public abstract class AoC2020Day<T> extends AoCDay<T> {

    protected abstract T parseInput(String strInput);

    protected abstract long partOne(T input);

    protected abstract long partTwo(T input);

    @Override
    protected String getYear() {

        return "aoc2020";
    }
}
