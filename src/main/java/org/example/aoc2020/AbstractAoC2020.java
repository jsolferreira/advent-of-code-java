package org.example.aoc2016;

import org.example.AbstractBase;

public abstract class AbstractAoC2016<T, Q> extends AbstractBase<T, Q> {

    protected abstract Q parseInput(String strInput);

    protected abstract T partOne(Q input);

    protected abstract T partTwo(Q input);

    @Override
    protected String getYear() {

        return "aoc2016";
    }
}
