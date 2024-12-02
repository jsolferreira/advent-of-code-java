package org.example.aoc.aoc2016;

import org.example.base.AoCDay;

public abstract class AoC2016Day<T> extends AoCDay<T> {

    protected abstract T parseInput(String strInput);

    protected abstract Object partOne(T input);

    protected abstract Object partTwo(T input);

    @Override
    protected String getYear() {

        return "aoc2016";
    }
}
