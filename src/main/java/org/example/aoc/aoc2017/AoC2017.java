package org.example.aoc.aoc2017;

import org.example.base.AoCYear;
import org.example.base.Runnable;
import org.example.base.result.DayResult;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class AoC2017 extends AoCYear {

    @Override
    protected String getYear() {

        return "2017";
    }

    @Override
    protected List<Class<? extends Runnable<DayResult>>> getDays() {

        return List.of(
                Day01.class
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
