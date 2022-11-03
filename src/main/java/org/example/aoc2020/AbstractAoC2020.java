package org.example.aoc2020;

import org.example.base.AbstractAoCDay;

public abstract class AbstractAoC2020<T> extends AbstractAoCDay<T> {

    protected abstract T parseInput(String strInput);

    protected abstract long partOne(T input);

    protected abstract long partTwo(T input);

    @Override
    protected String getYear() {

        return "aoc2020";
    }
}
