package org.example.aoc.aoc2020;

import org.example.base.AoCYear;
import org.example.base.Runnable;
import org.example.base.result.DayResult;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class AoC2020 extends AoCYear {

    @Override
    protected String getYear() {

        return "2020";
    }

    @Override
    protected List<Class<? extends Runnable<DayResult>>> getDays() {

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
                Day19.class,
                Day20.class
        );
    }

    @Override
    protected Runnable<DayResult> newDayInstance(Class<? extends Runnable<DayResult>> c) throws NoSuchMethodException,
                                                                                                InvocationTargetException,
                                                                                                InstantiationException,
                                                                                                IllegalAccessException {

        return c.getDeclaredConstructor().newInstance();
    }
}
