package org.example.aoc.aoc2020;

import org.example.base.AoCYear;
import org.example.base.Runnable;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class AoC2020 extends AoCYear {

    @Override
    protected List<Class<? extends Runnable>> getDays() {

        return List.of(
                Day01.class,
                Day02.class,
                Day03.class,
                Day04.class,
                Day05.class,
                Day06.class,
                Day07.class,
                Day08.class,
                Day09.class,
                Day10.class,
                Day11.class,
                Day12.class,
                Day13.class,
                Day14.class,
                Day15.class,
                Day16.class,
                Day17.class,
                Day18.class,
                Day19.class
        );
    }

    @Override
    protected Runnable newDayInstance(Class<? extends Runnable> c) throws NoSuchMethodException,
                                                                          InvocationTargetException,
                                                                          InstantiationException,
                                                                          IllegalAccessException {

        return c.getDeclaredConstructor().newInstance();
    }
}
