package org.example.aoc.aoc2019;

import org.example.base.AoCDay;

public abstract class AoC2019Day<T> extends AoCDay<T, Long> {

    protected abstract T parseInput(String strInput);

    protected abstract Long partOne(T input);

    protected abstract Long partTwo(T input);

    @Override
    protected String getYear() {

        return "aoc2019";
    }
}
