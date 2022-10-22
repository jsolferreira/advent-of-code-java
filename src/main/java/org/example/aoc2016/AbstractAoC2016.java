package org.example.aoc2016;

import org.example.base.AbstractAoCDay;

public abstract class AbstractAoC2016<T, Q> extends AbstractAoCDay<T, Q> {

    protected abstract Q parseInput(String strInput);

    protected abstract T partOne(Q input);

    protected abstract T partTwo(Q input);

    @Override
    protected String getYear() {

        return "aoc2016";
    }
}
