package org.example.aoc2016;

import org.example.base.AbstractYearRunner;
import org.example.base.Runnable;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class AoC2016 extends AbstractYearRunner {

    @Override
    protected List<Class<? extends Runnable>> getDays() {

        return List.of(
                Day01.class,
                Day02.class
        );
    }

    @Override
    protected Runnable newInstance(Class<? extends Runnable> c) throws NoSuchMethodException, InvocationTargetException, InstantiationException,
                                                                       IllegalAccessException {

        return c.getDeclaredConstructor().newInstance();
    }
}
