package org.example.aoc2020;

import org.example.base.AbstractYear;
import org.example.base.Base;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class AoC2020 extends AbstractYear {

    @Override
    protected List<Class<? extends Base>> getDays() {

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
                Day10.class
        );
    }

    @Override
    protected Base newInstance(Class<? extends Base> c) throws NoSuchMethodException,
                                                               InvocationTargetException,
                                                               InstantiationException,
                                                               IllegalAccessException {

        return c.getDeclaredConstructor().newInstance();
    }
}
